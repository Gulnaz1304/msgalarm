package com.app.msgalarmclock.presentation.ui.main.alarm

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.app.msgalarmclock.App
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.base.BaseFragment
import com.app.msgalarmclock.presentation.dialog.ConfirmDialogFragment
import com.app.msgalarmclock.presentation.dialog.ConfirmDialogParams
import com.app.msgalarmclock.presentation.extension.*
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.model.ContactModel
import com.app.msgalarmclock.presentation.ui.main.choosecontact.SelectContactFragment
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_alarm

    override var toolbarTitle = R.string.new_alarm

    lateinit var viewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        timePicker.setIs24HourView(true)
        initSliders()

        setFragmentResultListener(SelectContactFragment.REQUEST_CODE_CONTACT) { requestCode, result ->
            if (requestCode == SelectContactFragment.REQUEST_CODE_CONTACT) {
                (result.getSerializable(SelectContactFragment.KEY_CONTACT_MODEL) as? ContactModel)?.let {
                    viewModel.setSelectedContact(it)
                }
            }
        }

        inputSelectedContact.setOnClickListener {
            viewModel.selectContact()
        }

        viewModel.selectContact.observe(viewLifecycleOwner) {
            navigateTo(SelectContactFragment.getInstance(it))
        }

        viewModel.init(
            arguments?.getSerializable(KEY_ALARM_MODEL) as? AlarmModel,
            arguments?.getSerializable(KEY_MODE) as? AlarmMode
        )

        viewModel.alarmInfo.observe(viewLifecycleOwner) {
            displayAlarmInfo(it)
        }

        viewModel.contactInfo.observe(viewLifecycleOwner) {
            inputSelectedContact.setText(it.name)
        }

        viewModel.alarmSuccess.observe(viewLifecycleOwner, { mode ->
            mode?.apply {
                context?.showToast(
                    when (this) {
                        AlarmAction.ADD -> R.string.alarm_add_success
                        AlarmAction.UPDATE -> R.string.alarm_update_success
                        AlarmAction.REMOVE -> R.string.alarm_remove_success
                    }
                )
            }
            finish()
        })

        viewModel.daysNotSelected.observe(viewLifecycleOwner, {
            context?.showToast(R.string.select_at_least_one_day)
        })

        viewModel.contactNotSelected.observe(viewLifecycleOwner) {
            context?.showToast(R.string.contact_select_error)
        }

        viewModel.modeInfo.observe(viewLifecycleOwner) { mode ->
            if (mode == AlarmMode.INFO) {
                btnRemove.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        ConfirmDialogFragment.getInstance(
                            ConfirmDialogParams(
                                R.string.alarm_remove_confirm_title,
                                R.string.alarm_remove_confirm_desc,
                                R.string.cancel,
                                R.string.remove
                            )
                        ).apply {
                            onPositiveBtnClick = {
                                viewModel.deleteAlarm()
                            }
                            show(this@AlarmFragment.childFragmentManager, TAG_CONFIRM_REMOVE_DIALOG)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save) {
            viewModel.saveAlarm(
                timePicker.timeHour,
                timePicker.timeMinute,
                getSelectedDays(),
                inputDescription.text.toString(),
                sliderPauseDuration.value.toInt(),
                sliderPausesLimit.value.toInt()
            )
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun displayAlarmInfo(alarmModel: AlarmModel) = with(alarmModel) {
        timePicker.timeHour = hour
        timePicker.timeMinute = minute

        inputDescription.setText(text)

        sliderPauseDuration.value = alarmModel.pauseDuration.toFloat()
        sliderPausesLimit.value = alarmModel.pausesLimit.toFloat()

        (containerDays as? ChipGroup)
            ?.getChips()
            ?.filter { days.map { it.shortName }.contains(it.text) }
            ?.map { it.isChecked = true }
    }

    private fun initSliders() {
        sliderPauseDuration.apply {
            pauseDurationTitle.text = getString(R.string.pause_duration, value.toInt())
            addOnChangeListener { _, value, _ ->
                pauseDurationTitle.text = getString(R.string.pause_duration, value.toInt())
            }
        }

        sliderPausesLimit.apply {
            pausesLimitTitle.text = getString(R.string.pauses_limit, value.toInt())
            addOnChangeListener { _, value, _ ->
                pausesLimitTitle.text = getString(R.string.pauses_limit, value.toInt())
            }
        }
    }

    private fun getSelectedDays(): List<String> {
        return (containerDays as? ChipGroup)
            ?.getChips()
            ?.filter { it.isChecked }
            ?.map { it.text.toString() } ?: emptyList()
    }

    companion object {
        private const val KEY_ALARM_MODEL = "KEY_ALARM_MODEL"
        private const val KEY_MODE = "KEY_MODE"
        private const val TAG_CONFIRM_REMOVE_DIALOG = "TAG_CONFIRM_REMOVE_DIALOG"

        fun getInstance(alarm: AlarmModel? = null, mode: AlarmMode) = AlarmFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(KEY_ALARM_MODEL, alarm)
                putSerializable(KEY_MODE, mode)
            }
        }
    }
}