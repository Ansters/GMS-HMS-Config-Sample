package com.ansters.app.gmshms

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ansters.app.gmshms.common.Event
import com.ansters.app.gmshms.data.LocationLogModel
import com.ansters.app.mobileservice.LocationCallback
import com.ansters.app.mobileservice.MobileService
import java.lang.Exception
import java.util.*

class MainViewModel(
    private val context: Context,
    private val mobileService: MobileService
) : ViewModel() {

    private val _mobileServiceName = MutableLiveData(mobileService.getMobileServiceProviderName())
    val mobileServiceName: LiveData<String>
        get() = _mobileServiceName

    private val _mobileServiceAvailability = MutableLiveData(mobileService.isServiceAvailable())
    val mobileServiceAvailability: LiveData<Boolean>
        get() = _mobileServiceAvailability

    private val _requestPermission = MutableLiveData<Event<Unit>>()
    val requestPermission: LiveData<Event<Unit>>
        get() = _requestPermission

    private val _locationLogList = MutableLiveData(listOf<LocationLogModel>())
    val locationLogList: LiveData<List<LocationLogModel>>
        get() = _locationLogList

    fun getLastKnownLocation() {
        if (!isLocationGranted()) {
            _requestPermission.postValue(Event(Unit))
            return
        }
        mobileService.getLastKnownLocation(onGetLastKnownLocation)
    }

    private fun isLocationGranted() : Boolean {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    private val onGetLastKnownLocation = object : LocationCallback {
        override fun onLocationUpdate(location: Location) {
            val logModel = LocationLogModel(Date().toString(), "(${location.latitude}, ${location.longitude})")
            val newList = arrayListOf<LocationLogModel>()
            _locationLogList.value?.forEach { log ->
                newList.add(log)
            }
            newList.add(logModel)
            _locationLogList.postValue(newList)
        }
        override fun onFailed(ex: Exception) {
            val logModel = LocationLogModel(Date().toString(), ex.message.toString())
            val newList = arrayListOf<LocationLogModel>()
            _locationLogList.value?.forEach { log ->
                newList.add(log)
            }
            newList.add(logModel)
            _locationLogList.postValue(newList)
        }
    }

}