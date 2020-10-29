package com.designedbyz.sunrise

import android.app.Application
import com.designedbyz.sunrise.di.DaggerApplicationComponent

class SunriseApplication: Application() {

    val applicationComponent = DaggerApplicationComponent.builder().application(this).build()
}
