package com.app.msgalarmclock.data.repository.contact

import com.app.msgalarmclock.data.db.entity.ContactEntity

interface ContactRepository {
    fun getContacts(): List<ContactEntity>
}