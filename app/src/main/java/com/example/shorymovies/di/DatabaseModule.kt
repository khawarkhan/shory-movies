package com.example.shorymovies.di

import android.app.Application
import androidx.room.Room
import com.example.shorymovies.network.local.MovieDao
import com.example.shorymovies.network.local.ShoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * * created by Khawar Habib on 04/07/2023
 *
 * DatabaseModule is DI component, provides us with db related functionality
 *
 * helps us with different tab tables access
 *
 **/
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: ShoryDatabase.Callback): ShoryDatabase {
        return Room.databaseBuilder(application, ShoryDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: ShoryDatabase): MovieDao {
        return db.getArticleDao()
    }
}