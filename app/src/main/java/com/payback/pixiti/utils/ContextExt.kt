package com.payback.pixiti.utils

import android.content.Context
import android.widget.Toast

/*
 * Create an toast
 */
fun Context.toast(
        message: String,
        length: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, message, length).show()