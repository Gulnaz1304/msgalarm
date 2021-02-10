package com.app.msgalarmclock.presentation.model

import java.io.Serializable

data class AlarmModel(
    val id: Int,
    val hour: Int,
    val minute: Int,
    var isStarted: Boolean,
    val createdAt: Long,
    val text: String,
    val days: List<DayOfWeek>,
    val pauseDuration: Int,
    val pausesLimit: Int,
    var pausesLeft: Int,
    var contact: ContactModel
): Serializable