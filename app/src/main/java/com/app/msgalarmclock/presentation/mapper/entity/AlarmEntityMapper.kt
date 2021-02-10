package com.app.msgalarmclock.presentation.mapper.entity

import com.app.msgalarmclock.data.db.entity.AlarmEntity
import com.app.msgalarmclock.presentation.model.AlarmModel

object AlarmEntityMapper {

    fun map(model: AlarmModel): AlarmEntity = with(model) {
        return AlarmEntity(
            id,
            hour,
            minute,
            isStarted,
            text,
            createdAt,
            days.joinToString(", ") { it.shortName },
            pauseDuration,
            pausesLimit,
            pausesLeft,
            ContactEntityMapper.map(contact)
        )
    }

}