package com.example.shorymovies.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shorymovies.di.ApplicationScope
import com.example.shorymovies.network.model.movies.Movie
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * created by Khawar Habib on 04/07/2023
 *
 * Database for movies
 **/
@Database(entities = [Movie::class], version = 1)
abstract class ShoryDatabase : RoomDatabase() {

    /**
     * to access Movie table for movies
     */
    abstract fun getArticleDao(): MovieDao

    class Callback @Inject constructor(
        private val database: Provider<ShoryDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}