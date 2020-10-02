package com.designedbyz.sunrise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.designedbyz.sunrise.imageloader.CatService
import com.designedbyz.sunrise.imageloader.CatService.Companion.url
import com.designedbyz.sunrise.imageloader.DogService
import com.designedbyz.sunrise.time.SunriseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var catService: CatService
    @Inject lateinit var sunriseService: SunriseService
//    @Inject lateinit var dogService: DogService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as SunriseApplication).applicationComponent.injectInto(this)
        CoroutineScope(Dispatchers.IO).launch {
        Log.d("TEST", sunriseService.getSunRiseSetResults(36.0000000F, -4.0000000F, "2020-10-1").results.sunrise.toString())
            Log.d("TEST", catService.getCat().url )
//            Log.d("TEST", dogService.getDog().url)
        }
        //        Log.d("TEST", catService.getCat().url)
//        Log.d("TEST", dogService.getDog().url)
    }
}
