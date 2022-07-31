package com.udacity.asteroidradar.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroidsInfo")
data class Entities (
    @PrimaryKey(autoGenerate = true)
    val id: Long,                    val codename: String,
    val closeApproachDate: String,   val absoluteMagnitude: Double,
    val estimatedDiameter: Double,   val relativeVelocity: Double,
    val distanceFromEarth: Double,   val isPotentiallyHazardous: Boolean
)





fun List<Asteroid>.asDatabaseModel(): Array<Entities> {
    return map {
        Entities(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
fun List<Entities>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}