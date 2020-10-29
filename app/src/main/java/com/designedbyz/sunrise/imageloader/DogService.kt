package com.designedbyz.sunrise.imageloader

import retrofit2.http.GET

interface DogService {

    companion object {
        const val url = "https://random.dog"
    }

    @GET("/woof.json")
    suspend fun getDog() : Dog

}
