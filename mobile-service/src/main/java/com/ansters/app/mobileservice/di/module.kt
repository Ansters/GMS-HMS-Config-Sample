package com.ansters.app.mobileservice.di

import com.ansters.app.mobileservice.MobileService
import com.ansters.app.mobileservice.MobileServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mobileServiceModule = module {
    factory<MobileService> {
        MobileServiceImpl(androidContext())
    }
}