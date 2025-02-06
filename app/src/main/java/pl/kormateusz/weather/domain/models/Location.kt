package pl.kormateusz.weather.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val key: String,
)