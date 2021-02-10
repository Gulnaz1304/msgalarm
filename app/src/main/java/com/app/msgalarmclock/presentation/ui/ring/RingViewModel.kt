package com.app.msgalarmclock.presentation.ui.ring

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.app.msgalarmclock.data.repository.alarm.AlarmRepository
import com.app.msgalarmclock.presentation.mapper.entity.AlarmEntityMapper
import com.app.msgalarmclock.presentation.mapper.model.AlarmModelMapper
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import javax.inject.Inject

class RingViewModel @Inject constructor(
    private val repository: AlarmRepository,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    private var alarm: AlarmModel? = null
        set(value) {
            value?.let {
                alarmInfo.value = it
            }
            field = value
        }

    val alarmInfo = MutableLiveData<AlarmModel>()
    val notifyContact = MutableLiveData<String>()
    val finish = MutableLiveData<Boolean>()

    fun init(alarmId: Int) {
        repository.getAlarm(alarmId).map { AlarmModelMapper.map(it) }.observeForever {
            alarm = it
        }
    }

    fun pauseAlarm() {
        alarm?.let {
            it.pausesLeft--
            if (it.pausesLeft > 0) {
                scheduler.schedule(it, true)
                repository.changeAlarm(AlarmEntityMapper.map(it))
            } else {
                notifyContact.value = it.contact.number
                scheduler.schedule(it, false)
            }
        }
        finish.value = true
    }

    fun dismissAlarm() {
        alarm?.let {
            repository.changeAlarm(
                AlarmEntityMapper.map(
                    it.apply {
                        pausesLeft = pausesLimit
                    }
                )
            )
        }
        finish.value = true
    }
}