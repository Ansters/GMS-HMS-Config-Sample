package com.ansters.app.gmshms

import android.app.Application
import com.ansters.app.mobileservice.di.mobileServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(mobileServiceModule)
            modules(mainModule)
        }
    }

}