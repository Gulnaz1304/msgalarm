package com.app.msgalarmclock.presentation.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.map
import com.app.msgalarmclock.App
import com.app.msgalarmclock.data.repository.alarm.AlarmRepository
import com.app.msgalarmclock.presentation.mapper.model.AlarmModelMapper
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import javax.inject.Inject

class RestartAlarmsService : LifecycleService() {

    @Inject
    lateinit var repository: AlarmRepository

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onCreate() {
        App.appComponent?.inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        repository.getAlarms().map {
            it.map((AlarmModelMapper::map))
        }.observe(this, {
            it.forEach { alarm ->
                if (alarm.isStarted) {
                    alarmScheduler.schedule(alarm)
                }
            }
        })

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}