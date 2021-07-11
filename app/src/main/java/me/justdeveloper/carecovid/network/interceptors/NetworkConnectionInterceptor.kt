package me.justdeveloper.carecovid.network.interceptors

import me.justdeveloper.carecovid.network.NetworkConnectionUtil
import me.justdeveloper.carecovid.network.exceptions.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkConnectionUtil.INSTANCE.isConnected()) {
            throw NoConnectionException()
        }

        return chain.proceed(chain.request())
    }
}
