package com.hasankanal.kotlinmovieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult


@Database(entities = [MovieResult::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object{
        @Volatile
        var instance : MovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : MovieDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movies.db"
                ).build()
            }
            return instance as MovieDatabase
        }
    }


}