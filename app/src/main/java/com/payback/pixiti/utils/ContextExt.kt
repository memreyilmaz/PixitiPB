package com.payback.pixiti.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

/*
 * Create an toast
 */
fun Context.toast(
        message: String,
        length: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, message, length).show()

/*
 * Create an toast
 */
fun Context.toastForDetail(
        message: String,
        length: Int = Toast.LENGTH_SHORT
) {
    val toast = Toast.makeText(this, message, length)
    toast.setGravity(Gravity.BOTTOM, 0 , 200)
    toast.show()
}

/*
 * Create an alert dialog
 */
fun Context.showAlertDialog(
        title: String,
        message: String,
        isCancellable: Boolean = true,
        negativeButtonText: String,
        positiveButtonText: String,
        negativeButtonListener: (() -> Unit)? = null,
        positiveButtonListener: () -> Unit
) =
        run {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle(title)
                setMessage(message)
            }
            builder.setNegativeButton(negativeButtonText) { dialog, _ ->
                negativeButtonListener?.invoke() ?: run {
                    dialog.cancel()
                }
            }
            builder.setPositiveButton(positiveButtonText) { dialog, _ ->
                positiveButtonListener.invoke()
                dialog.cancel()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(isCancellable)
            alertDialog.show()
        }