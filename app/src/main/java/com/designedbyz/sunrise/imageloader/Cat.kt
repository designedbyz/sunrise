package com.designedbyz.sunrise.imageloader

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Cat @JsonCreator constructor(@JsonProperty("file") val url:String)
