package com.app.msgalarmclock.presentation.ui.main.choosecontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.model.ContactModel
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    var items: List<ContactModel> = emptyList()
        set(value) {
            field = value
            states = BooleanArray(value.size)
            notifyDataSetChanged()
        }

    var states = BooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getCheckedItem(): ContactModel? {
        val checkedPosition = states.indexOf(true)
        return if (checkedPosition >= 0) {
            items[checkedPosition]
        } else {
            null
        }
    }

    fun setCheckedItem(contactId: Int) {
        items.find { it.id == contactId }?.let {
            val position = items.indexOf(it)
            states[position] = true
            notifyItemChanged(position)
        }
    }

    private fun clearCheckedContacts() {
        states.forEachIndexed { index, checked ->
            if (checked) {
                states[index] = false
                notifyItemChanged(index)
            }
        }
    }

    inner class ContactViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind() = with(itemView) {
            val item = items[adapterPosition]

            nameContact.text = item.name
            numberContact.text = item.number

            checkboxContact.isChecked = states[adapterPosition]

            setOnClickListener {
                clearCheckedContacts()
                checkboxContact.isChecked = !checkboxContact.isChecked
                states[adapterPosition] = checkboxContact.isChecked
            }
        }
    }

}