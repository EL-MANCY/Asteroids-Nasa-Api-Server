package com.udacity.asteroidradar.repository

import android.util.Log
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.dataBase.AsteroidDB
import com.udacity.asteroidradar.dataBase.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repo(private val database: AsteroidDB) {
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroid = AsteroidApi.retrofitService.getAsteroids(API_KEY)
                val result = parseAsteroidsJsonResult(JSONObject(asteroid))
                Log.i("Tagxx",result.toString())
                database.Dao.insert(*result.asDatabaseModel())
                Log.i("tagxxx",database.Dao.getAsteroidsDB().toString())
            } catch (err: Exception) {
                Log.e("Failed: AsteroidRepFile", err.message.toString())
            }
        }
    }
}