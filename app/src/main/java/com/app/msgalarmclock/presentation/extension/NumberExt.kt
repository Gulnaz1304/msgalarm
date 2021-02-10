package com.app.msgalarmclock.presentation.extension

import java.util.*

fun Int.presentationTime(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun generateId(): Int {
    return Random().nextInt(Int.MAX_VALUE)
}