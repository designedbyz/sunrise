package com.designedbyz.sunrise.permissions

import android.Manifest
import android.content.Context
import androidx.core.content.PermissionChecker


class LocationPermissionRequestUseCae {

    fun hasGrantedLocationPermission(context: Context): Boolean{
        return PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED
    }
}