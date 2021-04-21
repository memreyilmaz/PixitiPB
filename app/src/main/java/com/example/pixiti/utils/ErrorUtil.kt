package com.example.pixiti.utils

import android.content.Context
import androidx.paging.LoadState
import com.example.pixiti.R
import retrofit2.HttpException
import java.net.UnknownHostException

class ErrorUtil {
    companion object {
        /*
         * Handle LoadState.Error
         */
        fun handleError(error: LoadState.Error, context: Context) {
            context.applicationContext.apply {
                when (error.error) {
                    is UnknownHostException -> {
                        this.toast(getString(R.string.desc_error_connection))
                    }
                    is HttpException -> {
                        if ((error.error as HttpException).code() == TOO_MANY_REQUESTS_CODE) {
                            this.toast(getString(R.string.desc_error_limit))
                        } else {
                            this.toast("${error.error.message}")
                        }
                    }
                    else -> {
                        this.toast("${error.error.message}")
                    }
                }
            }
        }

        private const val TOO_MANY_REQUESTS_CODE = 429
    }
}