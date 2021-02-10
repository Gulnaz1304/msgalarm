package com.app.msgalarmclock.data.repository.contact

import android.content.ContentResolver
import android.provider.ContactsContract
import com.app.msgalarmclock.data.db.entity.ContactEntity
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : ContactRepository {

    override fun getContacts(): List<ContactEntity> {
        val contacts = ArrayList<ContactEntity>()
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor.moveToNext()) {
                contacts.add(
                    ContactEntity(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(numberIndex)
                    )
                )
            }
        }

        return contacts.sortedBy { it.name }
    }

    companion object {
        private val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }
}