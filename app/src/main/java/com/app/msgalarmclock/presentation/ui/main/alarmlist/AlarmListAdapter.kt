package com.app.msgalarmclock.presentation.ui.main.alarmlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.extension.presentationTime
import com.app.msgalarmclock.presentation.model.AlarmModel
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmListAdapter : RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder>() {

    var items = emptyList<AlarmModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onAlarmClick: ((AlarmModel) -> Unit)? = null
    var onAlarmCheckedChange: ((Boolean, AlarmModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(v)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val item = items[adapterPosition]

            itemAlarmTime.text = context.getString(
                R.string.time_pattern,
                item.hour.presentationTime(),
                item.minute.presentationTime()
            )
            itemAlarmDescription.text = item.text
            itemAlarmDays.text = if (items.size == ALL_DAYS_OF_WEEK) {
                context.getString(R.string.every_day)
            } else {
                item.days.joinToString(", ") { it.shortName }
            }

            itemAlarmSwitcher.apply {
                isChecked = item.isStarted
                setOnClickListener {
                    onAlarmCheckedChange?.invoke(itemAlarmSwitcher.isChecked, item)
                }
            }

            setOnClickListener {
                onAlarmClick?.invoke(item)
            }
        }
    }

    companion object {
        private const val ALL_DAYS_OF_WEEK = 7
    }
}