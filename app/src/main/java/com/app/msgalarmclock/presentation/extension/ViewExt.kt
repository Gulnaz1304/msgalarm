package com.app.msgalarmclock.presentation.extension

import android.os.Build
import android.widget.TimePicker
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


var TimePicker.timeHour: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour
        } else {
            currentHour
        }
    }
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = value
        } else {
            currentHour = value
        }
    }

var TimePicker.timeMinute: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minute
        } else {
            currentMinute
        }
    }
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minute = value
        } else {
            currentMinute = value
        }
    }

fun ChipGroup.getChips(): List<Chip> {
    return (0 until childCount)
        .map { getChildAt(it) }
        .filterIsInstance(Chip::class.java)
}
