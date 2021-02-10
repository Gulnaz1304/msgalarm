package com.app.msgalarmclock.presentation.mapper.model

import com.app.msgalarmclock.data.db.entity.AlarmEntity
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.model.DayOfWeek

object AlarmModelMapper {

    fun map(entity: AlarmEntity): AlarmModel =
        with(entity) {
            return AlarmModel(
                id,
                hour,
                minute,
                isStarted,
                createdAt,
                text,
                mapDays(days),
                pauseDuration,
                pausesLimit,
                pausesLeft,
                ContactModelMapper.map(entity.contact)
            )
        }

    private fun mapDays(days: String): List<DayOfWeek> {
        return days.split(", ")
            .filter { it.isNotBlank() }
            .map { DayOfWeek.fromShortName(it) }
    }
}