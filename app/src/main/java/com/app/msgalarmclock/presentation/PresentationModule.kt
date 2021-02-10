package com.app.msgalarmclock.presentation

import android.content.Context
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import com.app.msgalarmclock.presentation.util.AlarmSchedulerImpl
import com.app.msgalarmclock.presentation.viewmodeldi.ViewModelBinder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelBinder::class])
class PresentationModule {

    @Provides
    @Singleton
    fun provideAlarmScheduler(context: Context): AlarmScheduler {
        return AlarmSchedulerImpl(context)
    }
}