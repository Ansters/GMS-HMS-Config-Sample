package com.ansters.app.gmshms

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansters.app.gmshms.common.EventObserver
import com.ansters.app.gmshms.data.LocationLogModel
import com.ansters.app.gmshms.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var mAdapter: LocationLogAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialAdapter()
    }

    override fun onResume() {
        super.onResume()
        attachObserver()
    }

    fun onFetchLocation(view: View) {
        viewModel.getLastKnownLocation()
    }

    private fun initialAdapter() {
        mAdapter = LocationLogAdapter()
        binding.rvLocation.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                stackFromEnd = true
            }
        }
    }

    private fun attachObserver() {
        with(viewModel) {
            mobileServiceName.observe(this@MainActivity, mobileServiceNameObserver)
            mobileServiceAvailability.observe(this@MainActivity, mobileServiceAvailabilityObserver)
            requestPermission.observe(this@MainActivity, requestLocationPermissionObserver)
            locationLogList.observe(this@MainActivity, locationLogListObserver)
        }
    }

    private val mobileServiceAvailabilityObserver = Observer<Boolean> { available ->
        if (!available) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle(R.string.title_mobile_service_not_available)
                .setPositiveButton(R.string.btn_ok) { _, _ ->
                    finishAffinity()
                }
                .create()
                .show()
        }
    }

    private val mobileServiceNameObserver = Observer<String> { title ->
        binding.toolbar.title = title
    }

    private val requestLocationPermissionObserver = EventObserver<Unit> {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED -> {
                return@EventObserver
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION)
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION)
            }
        }
    }

    private val locationLogListObserver = Observer<List<LocationLogModel>> { list ->
        mAdapter?.submitList(list)
        var size = list.size - 1
        if (size < 0) size = 0
        binding.rvLocation.smoothScrollToPosition(size)
    }

    companion object {
        const val REQUEST_PERMISSION = 0x104
    }

}