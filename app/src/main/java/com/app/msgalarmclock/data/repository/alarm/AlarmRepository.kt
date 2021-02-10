package com.app.msgalarmclock.data.repository.alarm

import androidx.lifecycle.LiveData
import com.app.msgalarmclock.data.db.entity.AlarmEntity

interface AlarmRepository {
    fun getAlarm(id: Int): LiveData<AlarmEntity>
    fun getAlarms(): LiveData<List<AlarmEntity>>
    fun addAlarm(entity: AlarmEntity)
    fun changeAlarm(entity: AlarmEntity)
    fun deleteAlarm(entity: AlarmEntity)
}