package com.app.msgalarmclock.presentation.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.receiver.AlarmBroadcastReceiver
import java.util.*

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    override fun schedule(alarmModel: AlarmModel, delayMode: Boolean) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.KEY_BUNDLE_ALARM, Bundle().apply {
            putSerializable(AlarmBroadcastReceiver.KEY_ALARM, alarmModel)
        })

        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmModel.id, intent, P_INTENT_FLAG)
        val alarmTime = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            if (delayMode) {
                add(Calendar.MINUTE, alarmModel.pauseDuration)
            } else {
                set(Calendar.HOUR_OF_DAY, alarmModel.hour)
                set(Calendar.MINUTE, alarmModel.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        if (alarmTime.timeInMillis < System.currentTimeMillis()) {
            alarmTime.set(Calendar.DAY_OF_MONTH, alarmTime.get(Calendar.DAY_OF_MONTH) + 1)
        }

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            alarmTime.timeInMillis,
            pendingIntent
        )
    }

    override fun cancel(alarmId: Int) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        alarmManager?.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmId,
                intent,
                P_INTENT_FLAG
            )
        )
    }

    companion object {
        private const val P_INTENT_FLAG = 0
    }
}