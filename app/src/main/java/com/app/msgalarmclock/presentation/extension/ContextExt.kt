package com.app.msgalarmclock.presentation.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(text: Int) {
    showToast(getString(text))
}