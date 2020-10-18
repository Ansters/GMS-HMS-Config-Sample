package com.ansters.app.gmshms

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(
            context = androidContext(),
            mobileService = get()
        )
    }
}