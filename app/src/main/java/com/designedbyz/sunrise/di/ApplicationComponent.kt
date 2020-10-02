package com.designedbyz.sunrise.di

import com.designedbyz.sunrise.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun injectInto(mainActivity: MainActivity) //a more readable twist on the standard "inject" method
}
