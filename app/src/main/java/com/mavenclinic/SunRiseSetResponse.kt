package com.mavenclinic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunRiseSetResponse(@SerialName("results") val results: SunRiseSetResults,
                              @SerialName("status") val status: String)

