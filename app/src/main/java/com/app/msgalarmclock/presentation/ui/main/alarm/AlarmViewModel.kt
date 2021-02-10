package com.app.msgalarmclock.presentation.ui.main.alarm

import androidx.lifecycle.MutableLiveData
import com.app.msgalarmclock.data.repository.alarm.AlarmRepository
import com.app.msgalarmclock.presentation.base.BaseViewModel
import com.app.msgalarmclock.presentation.extension.generateId
import com.app.msgalarmclock.presentation.mapper.entity.AlarmEntityMapper
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.model.ContactModel
import com.app.msgalarmclock.presentation.model.DayOfWeek
import com.app.msgalarmclock.presentation.util.AlarmScheduler
import com.app.msgalarmclock.presentation.util.SingleLiveEvent
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val repository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : BaseViewModel() {

    private var alarmModel: AlarmModel? = null
        set(value) {
            value?.let {
                alarmInfo.postValue(it)
            }
            field = value
        }

    private var mode: AlarmMode? = null
        set(value) {
            value?.let {
                modeInfo.postValue(it)
            }
            field = value
        }

    private var contactModel: ContactModel? = null
        set(value) {
            value?.let {
                contactInfo.postValue(it)
            }
            field = value
        }

    val alarmSuccess = SingleLiveEvent<AlarmAction>()
    val daysNotSelected = SingleLiveEvent<Boolean>()
    val contactNotSelected = SingleLiveEvent<Boolean>()

    val alarmInfo = MutableLiveData<AlarmModel>()
    val modeInfo = MutableLiveData<AlarmMode>()
    val contactInfo = MutableLiveData<ContactModel>()

    val selectContact = SingleLiveEvent<Int?>()

    fun init(alarm: AlarmModel?, mode: AlarmMode?) {
        alarm?.let {
            this.alarmModel = it
            this.contactModel = it.contact
        }

        this.mode = mode
    }

    fun saveAlarm(
        hour: Int,
        minute: Int,
        days: List<String>,
        text: String,
        pauseDuration: Int,
        pausesLimit: Int
    ) {
        if (days.isEmpty()) {
            daysNotSelected.value = true
            return
        }
        if (contactModel == null) {
            contactNotSelected.value = true
            return
        }

        val model = AlarmModel(
            alarmModel?.id ?: generateId(),
            hour,
            minute,
            true,
            System.currentTimeMillis(),
            text.takeIf { it.isNotBlank() } ?: DEFAULT_ALARM_TEXT,
            days.map { DayOfWeek.fromShortName(it) },
            pauseDuration,
            pausesLimit,
            pausesLimit,
            contactModel ?: ContactModel()
        )
        alarmModel = model

        if (mode == AlarmMode.NEW) {
            repository.addAlarm(AlarmEntityMapper.map(model))
            alarmSuccess.value = AlarmAction.ADD
        } else {
            repository.changeAlarm(AlarmEntityMapper.map(model))
            alarmSuccess.value = AlarmAction.UPDATE
        }
        alarmScheduler.schedule(model)
    }

    fun deleteAlarm() {
        alarmModel?.let {
            repository.deleteAlarm(AlarmEntityMapper.map(it))
            alarmScheduler.cancel(it.id)
            alarmSuccess.value = AlarmAction.REMOVE
        }
    }

    fun selectContact() {
        selectContact.value = contactModel?.id
    }

    fun setSelectedContact(contactModel: ContactModel) {
        this.contactModel = contactModel
    }

    companion object {
        private const val DEFAULT_ALARM_TEXT = "Будильник"
    }
}