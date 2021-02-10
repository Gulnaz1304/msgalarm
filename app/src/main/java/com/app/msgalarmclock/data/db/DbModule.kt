package com.app.msgalarmclock.data.db

import android.content.Context
import androidx.room.Room
import com.app.msgalarmclock.data.db.dao.AlarmDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: AppDatabase): AlarmDao {
        return database.getAlarmDao()
    }

    companion object {
        private const val DB_NAME = "db_alarms"
    }
}