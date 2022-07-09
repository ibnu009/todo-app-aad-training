package com.dicoding.todoapp.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

fun Long.formatMilliToDateText(dateFormat: String): String {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.format(calendar.time)
}

fun Activity.showOkBackDialog(title: String, message: String, isFinishActivity: Boolean) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK") { p0, _ ->
            p0.dismiss()
            if (isFinishActivity) this@showOkBackDialog.finish()
        }
    }.create().show()
}