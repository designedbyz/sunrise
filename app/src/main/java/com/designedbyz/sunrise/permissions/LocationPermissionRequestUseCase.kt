package com.designedbyz.sunrise.permissions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.PermissionChecker
import com.designedbyz.sunrise.BuildProvider
import com.designedbyz.sunrise.LogUtil
import com.designedbyz.sunrise.R
import javax.inject.Inject


class LocationPermissionRequestUseCase @Inject constructor(
    private val buildProvider: BuildProvider,
    private val logUtil: LogUtil){

    companion object {
        const val PERMISSION_CHECK_REQUEST_CODE = 101
    }

    fun hasGrantedLocationPermission(context: Context): Boolean{
        if (buildProvider.isApiLevelOrGreater(Build.VERSION_CODES.M)) {
            return true
        }
        return PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestLocationPermission(activity: Activity) {
        activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_CHECK_REQUEST_CODE)
    }

    fun checkPermissionRequestResponse (activity: Activity, requestCode: Int, permissions: Array<out String>,
                                        grantResults: IntArray) : Boolean {
        return if (requestCode == PERMISSION_CHECK_REQUEST_CODE &&
            permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            logUtil.w(this.javaClass.simpleName, "Location Permission Not Granted!")
            AlertDialog.Builder(activity).setTitle(activity.getString(R.string.location_permission_not_granted_title))
                .setMessage(activity.getString(R.string.location_permission_not_granted_message))
                .setPositiveButton(activity.getString(R.string.ok), null)
                .create().show()
            false
        }

    }
}
