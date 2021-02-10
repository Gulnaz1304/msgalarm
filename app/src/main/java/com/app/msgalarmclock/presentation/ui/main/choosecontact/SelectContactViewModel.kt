package com.app.msgalarmclock.presentation.ui.main.choosecontact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.msgalarmclock.data.repository.contact.ContactRepository
import com.app.msgalarmclock.presentation.mapper.model.ContactModelMapper
import com.app.msgalarmclock.presentation.model.ContactModel
import com.app.msgalarmclock.presentation.util.SingleLiveEvent
import javax.inject.Inject

class SelectContactViewModel @Inject constructor(
    repository: ContactRepository
) : ViewModel() {

    val contacts = repository.getContacts().map(ContactModelMapper::map)

    val actionSuccess = SingleLiveEvent<ContactModel>()
    val actionError = MutableLiveData<Boolean>()

    fun selectContact(contactModel: ContactModel?) {
        if (contactModel == null) {
            actionError.value = true
        } else {
            actionSuccess.value = contactModel
        }
    }
}