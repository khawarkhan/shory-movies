package com.example.shorymovies.di

import android.content.Context
import com.example.shorymovies.BuildConfig
import com.example.shorymovies.common.NetworkUtils.getOfflineInterceptor
import com.example.shorymovies.common.NetworkUtils.getOnlineInterceptor
import com.example.shorymovies.common.NetworkUtils.hasNetwork
import com.example.shorymovies.network.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * created by Khawar Habib on 25/06/2023
 *
 * AppModule is DI component, provides up with Common stuff that we require
 *
 * helps us avoid recreation/disposing of objects
 *
 * for instance, it just returns MovieService for APIs call
 *
 **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideMovieApi(@ApplicationContext context: Context): MoviesService {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            /**
             * interceptor for just logging
             */
            .addInterceptor(interceptor)
            /**
             * for offline caching
             */
            .addNetworkInterceptor(getOfflineInterceptor(context))
            /**
             * for online caching
             */
            .addInterceptor(getOnlineInterceptor())
            .cache(myCache)
            .addNetworkInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder() // .addHeader(Constant.Header, authToken)
                        .build()
                chain.proceed(request)
            }).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BaseURL)
            .client(client) // This line is important
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesService::class.java)
    }

}
