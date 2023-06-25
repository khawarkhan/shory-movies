package com.example.shorymovies.di

import android.content.Context
import com.example.shorymovies.common.Constants
import com.example.shorymovies.common.NetworkUtils.getOfflineInterceptor
import com.example.shorymovies.common.NetworkUtils.getOnlineInterceptor
import com.example.shorymovies.network.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
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

        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val myCache = Cache(context.cacheDir, cacheSize)

//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            /** for online caching  */
            .addNetworkInterceptor(getOnlineInterceptor())
            /** for offline caching */
            .addInterceptor(getOfflineInterceptor(context))
            .cache(myCache)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MoviesService::class.java)
    }

}
