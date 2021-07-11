package me.justdeveloper.carecovid.network

import com.google.gson.GsonBuilder
import me.justdeveloper.carecovid.network.converters.DateDeserializer
import me.justdeveloper.carecovid.network.interceptors.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object RestClient {
    private var apiService: ApiService? = null

    private var apiServiceWithAuth: ApiService? = null

    private lateinit var retrofit: Retrofit

    private fun getApiServiceWithoutAuth(): ApiService {
        if (apiService == null) {
            apiService = getRetrofitWithoutAuth()
                .create(ApiService::class.java)
        }
        apiServiceWithAuth =
            null // clear the apiServiceWithAuth in case of wrong calling this method
        return apiService!!
    }


    fun getApiService(): ApiService {
     return   getApiServiceWithoutAuth()
    }

    private fun getClientBuilder(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(NetworkConnectionInterceptor())
    }


    private fun buildRetrofit() {
        if (!RestClient::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .addConverterFactory(getConverter())
                .build()
        }
    }

    private fun getConverter(): GsonConverterFactory {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val dateDeserializer = DateDeserializer(formatter)

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .create()

        return GsonConverterFactory.create(gson)
    }

    private fun getRetrofitWithoutAuth(): Retrofit {
        buildRetrofit()
        retrofit = retrofit.newBuilder()
            .client(
                getClientBuilder()
                    .build()
            )
            .build()

        return retrofit
    }
}
