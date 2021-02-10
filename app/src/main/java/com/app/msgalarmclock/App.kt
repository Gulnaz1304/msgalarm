package com.app.msgalarmclock

import android.app.Application
import com.app.msgalarmclock.di.AppModule
import com.app.msgalarmclock.di.component.ApplicationComponent
import com.app.msgalarmclock.di.component.DaggerApplicationComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        var appComponent: ApplicationComponent? = null
    }
}