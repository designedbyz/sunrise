package com.designedbyz.sunrise.time

import com.mavenclinic.Result
import com.mavenclinic.SunRiseSetResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SunriseService {

    companion object {
        const val url = "https://api.sunrise-sunset.org"
    }

    @GET("/json")
    suspend fun getSunRiseSetResults(@Query("lat") lat:Float, @Query("lng") lng:Float,
                             @Query("date") date:String) : Result
}
