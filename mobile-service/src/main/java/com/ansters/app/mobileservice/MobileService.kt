package com.ansters.app.mobileservice

interface MobileService {
    fun getMobileServiceProviderName() : String
    fun isServiceAvailable() : Boolean
    fun getLastKnownLocation(callback: LocationCallback)
}