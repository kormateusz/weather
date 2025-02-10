package pl.kormateusz.weather.domain.extensions

import java.time.OffsetDateTime

fun String.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(this)