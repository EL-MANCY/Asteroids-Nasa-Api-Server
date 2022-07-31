package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.dataBase.asDomainModel
import com.udacity.asteroidradar.dataBase.getDatabase
import com.udacity.asteroidradar.repository.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class FilterAsteroid (val value: String) { TODAY("TODAY"), THAT_WEEK("THAT_WEEK"), ALL("ALL") }
class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val Repository = Repo(database)

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture: LiveData<PictureOfDay>
        get() = _picture

    private val _asteroidDetailNavigated = MutableLiveData<Asteroid?>()
    val asteroidDetailNavigated: LiveData<Asteroid?>
        get() = _asteroidDetailNavigated


    private var filterA =MutableLiveData(FilterAsteroid.ALL)

    val allAsteroids =database.Dao.getAsteroidsDB()
        .map { it.asDomainModel()
        }
    val WeekAsteroids=database.Dao.getAsteroidsByDate((LocalDateTime.now()).format(DateTimeFormatter.ISO_DATE),
        (LocalDateTime.now().plusDays(7)).format(DateTimeFormatter.ISO_DATE))
        .map { it.asDomainModel()
        }
    val TodayAsteroids= database.Dao.getAsteroidsByDay((LocalDateTime.now()).format(DateTimeFormatter.ISO_DATE))
        .map { it.asDomainModel()
        }

    val FullList:LiveData<List<Asteroid>> = Transformations.switchMap(filterA){ when(filterA.value){
            FilterAsteroid.ALL->allAsteroids
            FilterAsteroid.TODAY->TodayAsteroids
            else->WeekAsteroids
    }}
    fun filterChanged(filter: FilterAsteroid){
        filterA.value=filter

    }


    private val _properties = MutableLiveData<String>()
    val properties: LiveData<String>
        get() = _properties



    init {
        LoadAsteroids()
        viewModelScope.launch{
        refreshPicture()
        }
    }

   private fun LoadAsteroids() {
       viewModelScope.launch {
           Repository.refreshAsteroids()
           val x=database.Dao.getAsteroidsDB().toString()
           Log.i("tagxx",x.toString())
       }
   }
    private suspend fun refreshPicture() {
        withContext(Dispatchers.IO) {
            try {
                //postValue is used as its taking a value from backgroud thread
                _picture.postValue(AsteroidApi.retrofitService.getPicture(API_KEY))

            } catch (err: Exception) {
            }
        }
    }
    fun sendAsteroidDetails(asteroid: Asteroid){
        _asteroidDetailNavigated.value=asteroid
    }
    fun AsteroidDetailsNavigated() {
        _asteroidDetailNavigated.value = null
    }


}
//viewModelScope.launch {
//    try {
//        _properties.value=AsteroidApi.retrofitService.getAsteroids(API_KEY)
//        Log.i("tagxx",_properties.value.toString())
//
//    }catch (e:Exception){ }
//}