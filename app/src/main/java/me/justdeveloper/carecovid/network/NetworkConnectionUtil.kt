package me.justdeveloper.carecovid.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkConnectionUtil private constructor(
    context: Context
) {

    private val applicationContext = context.applicationContext

    companion object {
        /**
         * Use this value directly, it will be instantiated from the Application Instance
         */
        lateinit var INSTANCE: NetworkConnectionUtil
            private set

        /**
         * don't call this method unless if you really want to create a new Instance
         *
         * @param context
         * @param name
         */
        fun newInstance(context: Context): NetworkConnectionUtil {
            INSTANCE = NetworkConnectionUtil(context)
            return INSTANCE
        }
    }

    @Suppress("DEPRECATION")
    fun isConnected(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                // for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                // for vpn connections
                actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
