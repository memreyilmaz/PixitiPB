package com.example.pixiti.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pixiti.R

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
    toast.setGravity(Gravity.BOTTOM, 0, 200)
    toast.show()
}

/*
 * Create an alert dialog
 */
fun Context.showAlertDialog(
        title: String? = null,
        message: String,
        isCancellable: Boolean = true,
        negativeButtonText: String,
        positiveButtonText: String,
        negativeButtonListener: (() -> Unit)? = null,
        positiveButtonListener: () -> Unit
) =
        run {
            val builder = AlertDialog.Builder(this, R.style.AlertDialog)
            builder.apply {
                title?.let {
                    //Using custom TextView for long titles more than 2 lines
                    val titleTextView = TextView(context)
                    titleTextView.apply {
                        text = title
                        setPadding(60,40,60,20)
                        setTextColor(Color.WHITE)
                        textSize = 18.0f
                    }
                    setCustomTitle(titleTextView)
                }
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

/*
 * Close the keyboard
 */
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}