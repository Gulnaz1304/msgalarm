package com.app.msgalarmclock.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.msgalarmclock.data.db.dao.AlarmDao
import com.app.msgalarmclock.data.db.entity.AlarmEntity
import java.util.concurrent.Executors

@Database(entities = [AlarmEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

    companion object {
        private const val NUM_OF_THREADS = 4
        val executor = Executors.newFixedThreadPool(NUM_OF_THREADS)
    }
}