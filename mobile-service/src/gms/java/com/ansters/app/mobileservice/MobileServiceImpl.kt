package com.ansters.app.mobileservice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices

class MobileServiceImpl(private val context: Context) : MobileService {

    override fun getMobileServiceProviderName(): String {
        return context.getString(R.string.mobile_service)
    }

    override fun isServiceAvailable(): Boolean {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }

    override fun getLastKnownLocation(callback: LocationCallback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val service = LocationServices.getFusedLocationProviderClient(context)
        service.lastLocation
            .addOnSuccessListener { location ->
                callback.onLocationUpdate(location)
            }
            .addOnFailureListener { ex ->
                callback.onFailed(ex)
            }
    }

}