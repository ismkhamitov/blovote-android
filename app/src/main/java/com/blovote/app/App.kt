package com.blovote.app

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
    }

    companion object {
        lateinit var appComponent : AppComponent
    }
}