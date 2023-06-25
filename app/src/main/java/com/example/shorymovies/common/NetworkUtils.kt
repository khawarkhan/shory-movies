package com.example.shorymovies.common

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Request


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Utility class for work related to network
 *
 **/
object NetworkUtils {


    /**
     * check if connectivity available
     */
    fun hasNetwork(context: Context): Boolean {
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // use the latest if current phone version is greater than M, use legacy otherwise
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
            networkCapabilities != null
        } else {
            // below Marshmallow
            val activeNetwork = connManager.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable
        }
    }


    /**
     * online interceptor for cache response
     */
    fun getOnlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        }
    }


    /**
     * offline interceptor for cache response
     */
    fun getOfflineInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            if (!hasNetwork(context)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()

            }
            chain.proceed(request)
        }
    }


}