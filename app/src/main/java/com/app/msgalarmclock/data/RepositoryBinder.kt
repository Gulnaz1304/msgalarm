package com.app.msgalarmclock.data

import com.app.msgalarmclock.data.repository.alarm.AlarmRepository
import com.app.msgalarmclock.data.repository.alarm.AlarmRepositoryImpl
import com.app.msgalarmclock.data.repository.contact.ContactRepository
import com.app.msgalarmclock.data.repository.contact.ContactRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBinder {

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(impl: AlarmRepositoryImpl): AlarmRepository

    @Binds
    @Singleton
    abstract fun bindContactRepository(impl: ContactRepositoryImpl): ContactRepository
}