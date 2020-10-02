package com.designedbyz.sunrise

import android.app.Application
import com.designedbyz.sunrise.di.DaggerApplicationComponent

class SunriseApplication: Application() {

    val applicationComponent = DaggerApplicationComponent.create()
//    override fun onCreate() {
//        super.onCreate()
//    }
}
