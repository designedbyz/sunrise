package com.mavenclinic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(@SerialName("results") val results: SunRiseSetResults, @SerialName("status") val status: String) {

}
