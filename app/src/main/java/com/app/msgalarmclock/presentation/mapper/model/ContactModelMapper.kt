package com.app.msgalarmclock.presentation.mapper.model

import com.app.msgalarmclock.data.db.entity.ContactEntity
import com.app.msgalarmclock.presentation.model.ContactModel

object ContactModelMapper {

    fun map(entity: ContactEntity): ContactModel = with(entity) {
        return ContactModel(id, name, number)
    }
}