package com.designedbyz.sunrise.imageloader

import com.designedbyz.sunrise.time.SunriseService
import com.mavenclinic.SunRiseSetResponse
import com.mavenclinic.SunRiseSetResults
import com.mavenclinic.ApiTime
import com.soywiz.klock.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImageLoaderUseCaseTest {

    @RelaxedMockK lateinit var dogService: DogService
    @RelaxedMockK lateinit var catService: CatService
    @RelaxedMockK lateinit var sunriseService: SunriseService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val sunrise = ApiTime(Time(8.hours + 36.minutes))
        val sunset = ApiTime(Time(19.hours + 6.minutes))
        val sunRiseSetResults = SunRiseSetResults(sunrise, sunset)
        coEvery { sunriseService.getSunRiseSetResults(any(), any(), any()) } returns  SunRiseSetResponse(sunRiseSetResults, "")
        coEvery { catService.getCat()} returns Cat("I'm A Cat!")
        coEvery { dogService.getDog()} returns Dog("I'm A Dog!")
    }

    @Test
    @ExperimentalCoroutinesApi // spent more time than I would have liked on these, I'm normally in legacy code so a coroutine in a test was a bit baffling at first!
    fun tesCatCalledBetweenSunriseAndSunset() = kotlinx.coroutines.test.runBlockingTest  {
        val noon = DateTime(Date(2020, 10, 2), Time(12))
        val imageLoaderUseCase = ImageLoaderUseCase(catService, dogService, sunriseService)
        val url = imageLoaderUseCase.loadImageForLocation(0F, 0F, noon)
        assertEquals("I'm A Cat!", url)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testDogCalledBeforeSunrise() = kotlinx.coroutines.test.runBlockingTest  {
        val seven = DateTime(Date(2020, 10, 2), Time(7))
        val imageLoaderUseCase = ImageLoaderUseCase(catService, dogService, sunriseService)
        val url = imageLoaderUseCase.loadImageForLocation(0F, 0F, seven)
        assertEquals("I'm A Dog!", url)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testDogCalledAfterSunset() = kotlinx.coroutines.test.runBlockingTest  {
        val seven = DateTime(Date(2020, 10, 2), Time(20))
        val imageLoaderUseCase = ImageLoaderUseCase(catService, dogService, sunriseService)
        val url = imageLoaderUseCase.loadImageForLocation(0F, 0F, seven)
        assertEquals("I'm A Dog!", url)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testDogRejectsVideos() = kotlinx.coroutines.test.runBlockingTest {
        var count = 0
        coEvery { dogService.getDog()} answers {
            if (count == 0) {
                count++
                Dog("I'm a .mp4")
            } else {
                Dog("I'm A Dog!")
            }
        }
        val seven = DateTime(Date(2020, 10, 2), Time(7))
        val imageLoaderUseCase = ImageLoaderUseCase(catService, dogService, sunriseService)
        val url = imageLoaderUseCase.loadImageForLocation(0F, 0F, seven)
        assertEquals("I'm A Dog!", url)
        coVerify(exactly = 2) { dogService.getDog() }
    }
}
