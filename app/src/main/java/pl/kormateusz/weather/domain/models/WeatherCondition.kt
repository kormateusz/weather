package pl.kormateusz.weather.domain.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import pl.kormateusz.weather.R
import pl.kormateusz.weather.ui.theme.Cloudy
import pl.kormateusz.weather.ui.theme.Cold
import pl.kormateusz.weather.ui.theme.Hot
import pl.kormateusz.weather.ui.theme.Night
import pl.kormateusz.weather.ui.theme.Rainy
import pl.kormateusz.weather.ui.theme.Snowy
import pl.kormateusz.weather.ui.theme.Sunny

enum class WeatherCondition(val code: Int, val color: Color, @DrawableRes val image: Int) {
    SUNNY(1, Sunny, R.drawable.image_sunny),
    MOSTLY_SUNNY(2, Sunny, R.drawable.image_sunny_cloudy),
    PARTLY_SUNNY(3, Sunny, R.drawable.image_sunny_cloudy),
    INTERMITTENT_CLOUDS(4, Sunny, R.drawable.image_sunny_cloudy),
    HAZY_SUNSHINE(5, Sunny, R.drawable.image_sunny_cloudy),
    MOSTLY_CLOUDY(6, Cloudy, R.drawable.image_sunny_cloudy),
    CLOUDY(7, Cloudy, R.drawable.image_cloudy),
    DREARY(8, Cloudy, R.drawable.image_cloudy),
    FOG(11, Cloudy, R.drawable.image_cloudy),
    SHOWERS(12, Rainy, R.drawable.image_rainy),
    MOSTLY_CLOUDY_WITH_SHOWERS(13, Rainy, R.drawable.image_rainy),
    PARTLY_SUNNY_WITH_SHOWERS(14, Rainy, R.drawable.image_rainy),
    T_STORMS(15, Rainy, R.drawable.image_storm),
    MOSTLY_CLOUDY_WITH_T_STORMS(16, Rainy, R.drawable.image_storm),
    PARTLY_SUNNY_WITH_T_STORMS(17, Rainy, R.drawable.image_storm),
    RAIN(18, Rainy, R.drawable.image_rainy),
    FLURRIES(19, Snowy, R.drawable.image_snowy),
    MOSTLY_CLOUDY_WITH_FLURRIES(20, Cloudy, R.drawable.image_snowy),
    PARTLY_SUNNY_WITH_FLURRIES(21, Cloudy, R.drawable.image_snowy),
    SNOW(22, Snowy, R.drawable.image_snowy),
    MOSTLY_CLOUDY_WITH_SNOW(23, Snowy, R.drawable.image_snowy),
    ICE(24, Snowy, R.drawable.image_snowy),
    SLEET(25, Snowy, R.drawable.image_snowy),
    FREEZING_RAIN(26, Rainy, R.drawable.image_rainy),
    RAIN_AND_SNOW(29, Rainy, R.drawable.image_rain_snow),
    HOT(30, Hot, R.drawable.image_sunny),
    COLD(31, Cold, R.drawable.image_windy),
    WINDY(32, Cold, R.drawable.image_windy),
    CLEAR(33, Night, R.drawable.image_moon),
    MOSTLY_CLEAR(34, Night, R.drawable.image_cloudy_moon),
    PARTLY_CLOUDY(35, Night, R.drawable.image_cloudy_moon),
    INTERMITTENT_CLOUDS_NIGHT(36, Night, R.drawable.image_cloudy_moon),
    HAZY_MOONLIGHT(37, Night, R.drawable.image_cloudy_moon),
    MOSTLY_CLOUDY_NIGHT(38, Night, R.drawable.image_cloudy_moon),
    PARTLY_CLOUDY_WITH_SHOWERS(39, Night, R.drawable.image_rainy_night),
    MOSTLY_CLOUDY_WITH_SHOWERS_NIGHT(40, Night, R.drawable.image_rainy_night),
    PARTLY_CLOUDY_WITH_T_STORMS(41, Night, R.drawable.image_storm),
    MOSTLY_CLOUDY_WITH_T_STORMS_NIGHT(42, Night, R.drawable.image_storm),
    MOSTLY_CLOUDY_WITH_FLURRIES_NIGHT(43, Night, R.drawable.image_snowy_night),
    MOSTLY_CLOUDY_WITH_SNOW_NIGHT(44, Night, R.drawable.image_snowy_night),
    UNKNOWN(-1, Night, R.drawable.image_sunny);

    companion object {
        fun fromCode(code: Int): WeatherCondition = entries.find { it.code == code } ?: UNKNOWN
    }
}
