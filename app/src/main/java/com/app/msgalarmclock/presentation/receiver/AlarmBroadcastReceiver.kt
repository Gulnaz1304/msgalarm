package com.app.msgalarmclock.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.app.msgalarmclock.App
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.model.DayOfWeek
import com.app.msgalarmclock.presentation.service.RestartAlarmsService
import com.app.msgalarmclock.presentation.ui.ring.RingActivity
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import java.util.*
import javax.inject.Inject

class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        App.appComponent?.inject(this)

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            restartAlarms(context)
        } else {
            if (alarmIsToday(intent)) {
                startAlarmActivity(context, intent)
            }
        }
        (intent?.getSerializableExtra(KEY_ALARM) as? AlarmModel)?.let {
            alarmScheduler.schedule(it)
        }
    }

    private fun startAlarmActivity(context: Context?, intent: Intent?) {
        context?.let {
            intent?.apply {
                setClassName(it.packageName, RingActivity::class.java.name)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                it.startActivity(this)
            }
        }
    }

    private fun restartAlarms(context: Context?) {
        val i = Intent(context, RestartAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(i)
        } else {
            context?.startService(i)
        }
    }

    private fun alarmIsToday(intent: Intent?): Boolean {
        val currentDayNumber = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val alarmModel = intent?.getBundleExtra(KEY_BUNDLE_ALARM)?.getSerializable(KEY_ALARM) as? AlarmModel
        return alarmModel?.days?.contains(DayOfWeek.fromOrder(currentDayNumber)) ?: false
    }

    companion object {
        const val KEY_ALARM = "KEY_ALARM"
        const val KEY_BUNDLE_ALARM = "KEY_BUNDLE_ALARM"
    }
}