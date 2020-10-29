package com.designedbyz.sunrise

import android.os.Build
import javax.inject.Inject


class BuildProvider @Inject constructor() {

    fun isApiLevelOrGreater(apiLevel: Int): Boolean {
        return apiLevel >= Build.VERSION.SDK_INT
    }
}
