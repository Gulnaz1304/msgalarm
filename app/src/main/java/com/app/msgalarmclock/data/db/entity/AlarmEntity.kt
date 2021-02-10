package com.app.msgalarmclock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_alarms")
data class AlarmEntity(
    @PrimaryKey
    val id: Int = 0,
    val hour: Int = 0,
    val minute: Int = 0,
    @ColumnInfo(name = "is_started")
    val isStarted: Boolean = false,
    val text: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = 0,
    val days: String = "",
    @ColumnInfo(name = "pause_duration")
    val pauseDuration: Int = 0,
    @ColumnInfo(name = "pauses_limit")
    val pausesLimit: Int = 0,
    @ColumnInfo(name = "pauses_left")
    val pausesLeft: Int = 0,
    @Embedded(prefix = "contact_")
    var contact: ContactEntity
)