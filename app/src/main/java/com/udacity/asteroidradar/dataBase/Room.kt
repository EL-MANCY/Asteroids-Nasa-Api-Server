package com.udacity.asteroidradar.dataBase

import android.content.Context
import androidx.room.*

@Database(entities = [Entities::class], version = 1, exportSchema = false)
abstract class AsteroidDB : RoomDatabase() {
    abstract val Dao: Dao
}

private lateinit var INSTANCE: AsteroidDB

fun getDatabase(context: Context): AsteroidDB {
    synchronized(AsteroidDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDB::class.java,
                "asteroidInfo").build()
        }
    }
    return INSTANCE
}