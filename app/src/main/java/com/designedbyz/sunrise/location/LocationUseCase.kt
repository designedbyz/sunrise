package com.designedbyz.sunrise.location

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import com.designedbyz.sunrise.LogUtil
import com.designedbyz.sunrise.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val apiAvailability: GoogleApiAvailability,
    private val locationProviderClient: FusedLocationProviderClient,
    private val logUtil: LogUtil) {
    fun checkPlayServices(context: Context, apiAvaliableCallback: ApiAvaliableCallback) {
        when (val connectionResult =
            apiAvailability.isGooglePlayServicesAvailable(context)) {

            ConnectionResult.SUCCESS ->
                apiAvaliableCallback.apisAvailable(true)
            else -> {
                logUtil.w(this.javaClass.simpleName,
                    "Play Services Not Available for reason $connectionResult")
                apiAvaliableCallback.apisAvailable(false)
            }
        }
    }

    fun warnApisNotAvailable(context: Context) {
        AlertDialog.Builder(context).setTitle(context.getString(R.string.play_services_not_available_title))
            .setMessage(context.getString(R.string.play_services_not_available_message))
            .setPositiveButton(context.getString(R.string.ok), null)
            .create().show()
    }

    interface  ApiAvaliableCallback {
        fun apisAvailable(apisAvailable: Boolean)
    }

    @SuppressLint("MissingPermission") // Checked well above this method call
    fun getLastLocation(activity: Activity, onCompleteListener: OnCompleteListener<Location>) {
        locationProviderClient.lastLocation.addOnCompleteListener(activity, onCompleteListener)

    }
}
