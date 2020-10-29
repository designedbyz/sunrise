package com.designedbyz.sunrise.imageloader

import retrofit2.http.GET

interface CatService {
    companion object {
        const val url = "https://aws.random.cat"
    }

    @GET("/meow")
    suspend fun getCat() : Cat
}
