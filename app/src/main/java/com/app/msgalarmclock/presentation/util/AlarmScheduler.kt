package com.app.msgalarmclock.presentation.util

import com.app.msgalarmclock.presentation.model.AlarmModel

interface AlarmScheduler {
    fun schedule(alarmModel: AlarmModel, delayMode: Boolean = false)
    fun cancel(alarmId: Int)
}