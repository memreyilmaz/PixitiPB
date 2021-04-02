package com.payback.pixiti.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionUtil {
    companion object {
        private val storagePermissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        fun isStoragePermissionGranted(context: Context) =
                storagePermissions.isPermissionsGranted(context)
    }
}

/*
 * Permission check extension
 */
private fun Array<String>.isPermissionsGranted(context: Context): Boolean {
    for (i in this.indices) {
        if (ActivityCompat.checkSelfPermission(
                        context,
                        this[i]
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}