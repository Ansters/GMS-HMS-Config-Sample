package com.ansters.app.mobileservice

import android.location.Location
import java.lang.Exception

interface LocationCallback {
    fun onLocationUpdate(location: Location)
    fun onFailed(ex: Exception)
}