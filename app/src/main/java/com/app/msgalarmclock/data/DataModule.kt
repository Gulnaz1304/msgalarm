package com.app.msgalarmclock.data

import android.content.ContentResolver
import android.content.Context
import com.app.msgalarmclock.data.db.DbModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryBinder::class, DbModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }
}