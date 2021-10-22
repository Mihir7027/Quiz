package com.quiz.utils

import android.content.Context
import android.view.ContextThemeWrapper
import quiz.R
import java.util.*

class AndroidUtils {

    companion object {
        fun twoDigitAfterDecimal(number: Double): String {
            return String.format(Locale.US, "%.2f", number)
        }

        fun showAlertDialog(context: Context, message: String) {

            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(
                ContextThemeWrapper(context, R.style.DialogTheme)
            )
            builder.setMessage(message).setCancelable(false)
                .setPositiveButton(
                    R.string.ok
                ) { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }
    }

}