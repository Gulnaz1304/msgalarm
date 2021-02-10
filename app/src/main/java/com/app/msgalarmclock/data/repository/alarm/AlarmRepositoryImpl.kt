package com.app.msgalarmclock.data.repository.alarm

import androidx.lifecycle.LiveData
import com.app.msgalarmclock.data.db.AppDatabase
import com.app.msgalarmclock.data.db.dao.AlarmDao
import com.app.msgalarmclock.data.db.entity.AlarmEntity
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override fun getAlarm(id: Int): LiveData<AlarmEntity> {
        return alarmDao.getAlarm(id)
    }

    override fun getAlarms(): LiveData<List<AlarmEntity>> {
        return alarmDao.getAlarms()
    }

    override fun addAlarm(entity: AlarmEntity) {
        AppDatabase.executor.execute {
            alarmDao.insertAlarm(entity)
        }
    }

    override fun changeAlarm(entity: AlarmEntity) {
        AppDatabase.executor.execute {
            alarmDao.updateAlarm(entity)
        }
    }

    override fun deleteAlarm(entity: AlarmEntity) {
        AppDatabase.executor.execute {
            alarmDao.deleteAlarm(entity)
        }
    }
}