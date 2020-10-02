package com.mavenclinic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("results")
    data class SunRiseSetResults(
    @SerialName("sunrise")
    val sunrise: ApiTime,
    @SerialName("sunset")
    val sunset: ApiTime,
    @SerialName("solar_noon")
    val solarNoon: ApiTime? = null,
    @SerialName("civil_twilight_begin")
    val civilTwilightBegin: ApiTime? = null,
    @SerialName("civil_twilight_end")
    val civilTwilightEnd: ApiTime? = null,
    @SerialName("nautical_twilight_begin")
    val nauticalTwilightBegin: ApiTime? = null,
    @SerialName("nautical_twilight_end")
    val NauticalTwilightEnd: ApiTime? = null,
    @SerialName("astronomical_twilight_begin")
    val astronomicalTwilightBegin: ApiTime? = null,
    @SerialName("astronomical_twilight_end")
    val astronomicalTwilightEnd: ApiTime? = null
)
