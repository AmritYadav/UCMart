package com.ucmart.utils

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import com.ucmart.R
import org.joda.time.LocalTime

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showTimePickerDialog(time: LocalTime, callback: (selectedTime: LocalTime) -> Unit) {
    TimePickerDialog(
        this, R.style.AppTheme_DateTimePickerTheme,
        TimePickerDialog.OnTimeSetListener { _, hr, min ->
            callback.invoke(LocalTime(hr, min))
        },
        time.hourOfDay,
        time.minuteOfHour,
        false
    ).show()
}