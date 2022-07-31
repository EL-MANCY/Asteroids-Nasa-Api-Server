package com.udacity.asteroidradar.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM asteroidsInfo")
    fun getAsteroidsDB(): LiveData<List<Entities>>

    @Query("SELECT * FROM asteroidsInfo WHERE closeApproachDate = :start ")
    fun getAsteroidsByDay(start: String): LiveData<List<Entities>>

@Query("SELECT * FROM asteroidsInfo WHERE closeApproachDate BETWEEN :start AND :end")
fun getAsteroidsByDate(start: String, end: String): LiveData<List<Entities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroid: Entities)
}
