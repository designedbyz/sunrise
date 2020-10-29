package com.designedbyz.sunrise.di

import android.app.Application
import com.designedbyz.sunrise.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        fun build() : ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun injectInto(mainActivity: MainActivity) //a more readable twist on the standard "inject" method
}
