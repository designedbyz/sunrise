package com.designedbyz.sunrise.imageloader

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

//@Serializable
//data class Cat(@SerialName("file") val url: String)


data class Cat @JsonCreator constructor(@JsonProperty("file") val url:String)
