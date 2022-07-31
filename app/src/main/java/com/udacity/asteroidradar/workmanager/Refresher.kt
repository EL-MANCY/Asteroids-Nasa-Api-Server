package com.udacity.asteroidradar.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.dataBase.getDatabase
import com.udacity.asteroidradar.repository.Repo
import retrofit2.HttpException

class Refresher(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "Refresh"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = Repo(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}
