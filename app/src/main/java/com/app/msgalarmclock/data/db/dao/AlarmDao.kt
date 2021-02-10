package com.app.msgalarmclock.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.msgalarmclock.data.db.entity.AlarmEntity

@Dao
interface AlarmDao {

    @Insert
    fun insertAlarm(entity: AlarmEntity)

    @Update
    fun updateAlarm(entity: AlarmEntity)

    @Delete
    fun deleteAlarm(entity: AlarmEntity)

    @Query("SELECT * FROM table_alarms ORDER BY created_at ASC")
    fun getAlarms(): LiveData<List<AlarmEntity>>

    @Query("SELECT * FROM table_alarms WHERE id = :id")
    fun getAlarm(id: Int): LiveData<AlarmEntity>
}