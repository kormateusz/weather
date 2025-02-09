package pl.kormateusz.weather.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.kormateusz.weather.domain.models.Location

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: String,
    val name: String,
)

fun Location.toDatabaseEntity() = LocationEntity(
    id = key,
    name = name,
)

fun LocationEntity.toDomain() = Location(
    name = name,
    key = id,
)
