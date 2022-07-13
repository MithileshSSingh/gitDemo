package com.demo.gitdemo

import android.app.Application
import com.demo.gitdemo.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidContext(this@MainApplication)
            modules(applicationModule)
        }
    }
}