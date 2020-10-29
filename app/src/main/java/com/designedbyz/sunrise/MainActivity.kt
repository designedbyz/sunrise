package com.designedbyz.sunrise

import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import com.designedbyz.sunrise.imageloader.ImageLoaderUseCase
import com.designedbyz.sunrise.location.LocationUseCase
import com.designedbyz.sunrise.permissions.LocationPermissionRequestUseCase
import com.designedbyz.sunrise.time.SunriseService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.soywiz.klock.DateTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//Thoughts: The Activity is probably doing a bit too much here, there's both business logic and
//android related things going on in here. Given the chance, I'd like to go back and explore another
//layer of abstraction, as well as play with some MVI like Square's Workflow apis. Or maybe play around with Compose agian...
class MainActivity : AppCompatActivity(), OnCompleteListener<Location>, LocationUseCase.ApiAvaliableCallback {

    @Inject lateinit var imageLoaderUseCase: ImageLoaderUseCase
    @Inject lateinit var sunriseService: SunriseService
    @Inject lateinit var picasso: Picasso
    @Inject lateinit var permissionCheckUseCase: LocationPermissionRequestUseCase
    @Inject lateinit var locationUseCase: LocationUseCase
    @Inject lateinit var log: LogUtil

    private lateinit var cachedLocation: Location // todo, just make the cached one start out as a default?
    private val handler = Handler(Looper.getMainLooper())
    private var initialized = false
    private var permissionGranted : Boolean? = null
    private val runnable = Runnable {  getNextImage(cachedLocation) }
    private val defaultLocation = Location("")

    init {
        defaultLocation.latitude = 40.71455
        defaultLocation.longitude = -74.00712
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as SunriseApplication).applicationComponent.injectInto(this)
    }

    @RequiresApi(Build.VERSION_CODES.M) // it doesn't, actually, but the api level is checked in the use case.
    override fun onResume() {
        super.onResume()
        if (initialized) {
            getNextImage(cachedLocation)
        } else if (permissionGranted == null) {
            if (permissionCheckUseCase.hasGrantedLocationPermission(this)) {
                locationUseCase.checkPlayServices(this, this)
            } else {
                permissionCheckUseCase.requestLocationPermission(this)
            }
        } else {
            log.d(this.localClassName, "Permissions not granted, loading location from default location")
            cachedLocation = defaultLocation
            getNextImage(cachedLocation)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionGranted =
            permissionCheckUseCase.checkPermissionRequestResponse(this, requestCode, permissions, grantResults)

        permissionGranted?.let {
            if (it) {
                locationUseCase.checkPlayServices(this, this)
                return
            }
        }
        cachedLocation = defaultLocation
    }

    //Callback when location is complete
    override fun onComplete(task: Task<Location>) {
        if (!isFinishing && task.isSuccessful) {
            initialized = true
            val location = task.result
            if (location != null) {
                this.cachedLocation = location
                getNextImage(location)
            }
        } else if (!isFinishing) {
            log.w(this.localClassName, "Unable to get location, using default")
            cachedLocation = defaultLocation
            getNextImage(cachedLocation)
        }
    }

    private fun getNextImage(location: Location) {
        progress.visibility = VISIBLE
        fuzzy_pet.visibility = GONE
        val dateTime = DateTime.now()
        CoroutineScope(Dispatchers.Main).launch {
            val url = imageLoaderUseCase.loadImageForLocation(
                location.latitude.toFloat(),
                location.longitude.toFloat(), dateTime)
            log.d(this@MainActivity.localClassName, "url is $url")
            picasso.load(url).into(fuzzy_pet)
            progress.visibility = GONE
            fuzzy_pet.visibility = VISIBLE
        }
       handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(5L))
    }

    //Callback that is called when GooglePlay Service are available.
    override fun apisAvailable(apisAvailable: Boolean) {
        if (apisAvailable) {
            locationUseCase.getLastLocation(this, this)
        } else {
            locationUseCase.warnApisNotAvailable(this)
            cachedLocation = defaultLocation
            getNextImage(cachedLocation)
        }
    }
}
