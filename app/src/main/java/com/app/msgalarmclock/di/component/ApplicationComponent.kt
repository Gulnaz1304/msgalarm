package com.app.msgalarmclock.di.component

import com.app.msgalarmclock.data.DataModule
import com.app.msgalarmclock.di.AppModule
import com.app.msgalarmclock.presentation.PresentationModule
import com.app.msgalarmclock.presentation.receiver.AlarmBroadcastReceiver
import com.app.msgalarmclock.presentation.service.RestartAlarmsService
import com.app.msgalarmclock.presentation.ui.main.MainActivity
import com.app.msgalarmclock.presentation.ui.main.alarm.AlarmFragment
import com.app.msgalarmclock.presentation.ui.main.alarmlist.AlarmListFragment
import com.app.msgalarmclock.presentation.ui.main.choosecontact.SelectContactFragment
import com.app.msgalarmclock.presentation.ui.ring.RingActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, PresentationModule::class])
interface ApplicationComponent {
    fun inject(activity: RingActivity)
    fun inject(activity: MainActivity)
    fun inject(fragment: AlarmListFragment)
    fun inject(fragment: AlarmFragment)
    fun inject(fragment: SelectContactFragment)
    fun inject(service: RestartAlarmsService)
    fun inject(receiver: AlarmBroadcastReceiver)
}