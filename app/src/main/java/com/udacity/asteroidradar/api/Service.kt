package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



fun getInterceptor():HttpLoggingInterceptor{
    val loggingInterceptor =  HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
return loggingInterceptor
}
val logK= getInterceptor()

private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .readTimeout(120, TimeUnit.SECONDS)
    .addInterceptor(logK)
    .connectTimeout(120, TimeUnit.SECONDS)
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()


interface AsteroidService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("api_key") api: String):String
    @GET("planetary/apod")
    suspend fun getPicture(@Query("api_key") api: String): PictureOfDay
}

object AsteroidApi {
    val retrofitService : AsteroidService by lazy {
        retrofit.create(AsteroidService::class.java)
    }
}