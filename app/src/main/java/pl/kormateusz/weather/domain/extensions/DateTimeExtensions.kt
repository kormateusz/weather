package pl.kormateusz.weather.domain.extensions

import java.time.OffsetDateTime

fun String.toLocalDateTime(): OffsetDateTime = OffsetDateTime.parse(this)