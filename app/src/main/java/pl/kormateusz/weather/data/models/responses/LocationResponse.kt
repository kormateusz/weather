package pl.kormateusz.weather.data.models.responses

import com.google.gson.annotations.SerializedName
import pl.kormateusz.weather.domain.models.Location

data class LocationResponse(
    @SerializedName("LocalizedName") val name: String,
    @SerializedName("Key") val key: String,
)

fun LocationResponse.toDomain() = Location(
    name = name,
    key = key,
)

fun List<LocationResponse>.toDomain() = map { it.toDomain() }
