package com.app.msgalarmclock.presentation.ui.main.alarmlist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.msgalarmclock.App
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.base.BaseFragment
import com.app.msgalarmclock.presentation.extension.injectViewModel
import com.app.msgalarmclock.presentation.extension.presentationTime
import com.app.msgalarmclock.presentation.ui.main.alarm.AlarmFragment
import com.app.msgalarmclock.presentation.ui.main.alarm.AlarmMode
import kotlinx.android.synthetic.main.fragment_alarm_list.*
import java.util.*

class AlarmListFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_alarm_list

    lateinit var viewModel: AlarmListViewModel

    private var timer = Timer()

    private val timeHandler = Handler(Looper.getMainLooper())

    private val adapter = AlarmListAdapter().apply {
        onAlarmClick = {
            navigateTo(AlarmFragment.getInstance(it, AlarmMode.INFO))
        }
        onAlarmCheckedChange = { checked, model ->
            viewModel.onAlarmCheckedChange(checked, model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        setupTimer()
        setupRecyclerView()

        viewModel.alarms.observe(viewLifecycleOwner, {
            adapter.items = it
        })

        newAlarmFab.setOnClickListener {
            navigateTo(AlarmFragment.getInstance(mode = AlarmMode.NEW))
        }
    }

    override fun onDestroyView() {
        timer.cancel()
        timer = Timer()
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        listAlarms.apply {
            adapter = this@AlarmListFragment.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                timeHandler.post {
                    val c = Calendar.getInstance()
                    textTime?.text = getString(
                        R.string.time_pattern,
                        c.get(Calendar.HOUR_OF_DAY).presentationTime(),
                        c.get(Calendar.MINUTE).presentationTime(),
                    )
                }
            }
        }, TIME_UPDATE_DELAY, TIME_UPDATE_PERIOD)
    }

    companion object {
        private const val TIME_UPDATE_PERIOD = 1000L
        private const val TIME_UPDATE_DELAY = 0L

        fun getInstance() = AlarmListFragment()
    }
}