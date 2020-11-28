package com.hackyeah.app

import android.app.Application
import com.hackyeah.app.di.AppComponent
import com.hackyeah.app.di.DaggerAppComponent

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent =
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }
}