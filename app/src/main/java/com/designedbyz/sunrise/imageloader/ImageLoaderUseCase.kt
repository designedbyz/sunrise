package com.designedbyz.sunrise.imageloader

import com.designedbyz.sunrise.time.SunriseService
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import javax.inject.Inject

class ImageLoaderUseCase @Inject constructor(private val catService: CatService,
                                             private val dogService: DogService,
                                             private val sunriseService: SunriseService) {

    suspend fun loadImageForLocation(latitude: Float, longitude: Float, dateTime: DateTime) : String {
        val time = sunriseService.
            getSunRiseSetResults(latitude,longitude, dateTime.format(DateFormat.FORMAT_DATE)).results
        return if (time.sunrise.toTime() <= dateTime.time && time.sunset.toTime() >= dateTime.time) {
            catService.getCat()!!.url //todo: fix the cheating
        } else {
            var dogUrl = ""
            do {
                dogUrl = dogService.getDog()!!.url //todo: fix the cheating
            } while (dogUrl.endsWith(".mp4") || dogUrl.endsWith(".webm")) //surprise!
            // Picasso doesn't like these. a HEAD  request or actually playing the might be better,
            // but given empirical testing, this works well enough for the task at hand
            dogUrl
        }
    }
}
