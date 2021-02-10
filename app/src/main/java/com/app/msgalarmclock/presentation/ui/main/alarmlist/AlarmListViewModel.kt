package com.app.msgalarmclock.presentation.ui.main.alarmlist

import androidx.lifecycle.map
import com.app.msgalarmclock.data.repository.alarm.AlarmRepository
import com.app.msgalarmclock.presentation.base.BaseViewModel
import com.app.msgalarmclock.presentation.mapper.entity.AlarmEntityMapper
import com.app.msgalarmclock.presentation.mapper.model.AlarmModelMapper
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import javax.inject.Inject

class AlarmListViewModel @Inject constructor(
    private val repository: AlarmRepository,
    private val scheduler: AlarmScheduler
) : BaseViewModel() {

    val alarms = repository.getAlarms().map {
        it.map(AlarmModelMapper::map)
    }

    fun onAlarmCheckedChange(checked: Boolean, model: AlarmModel) {
        if (checked) {
            scheduler.schedule(model)
        } else {
            scheduler.cancel(model.id)
        }
        model.isStarted = checked
        repository.changeAlarm(AlarmEntityMapper.map(model))
    }
}