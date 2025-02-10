package pl.kormateusz.weather.domain.extensions

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatter {

    fun toFullDate(dateTime: OffsetDateTime): String =
        dateTime.format(DateTimeFormatter.ofPattern(FULL_DATE_TIME_FORMAT))

    fun toShortDate(dateTime: OffsetDateTime): String =
        dateTime.format(DateTimeFormatter.ofPattern(SHORT_DATE_TIME_FORMAT))

    private companion object {
        const val FULL_DATE_TIME_FORMAT = "dd MMMM yyyy, HH:mm"
        const val SHORT_DATE_TIME_FORMAT = "dd MMM"
    }
}