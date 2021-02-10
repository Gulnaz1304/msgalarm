package com.app.msgalarmclock.presentation.mapper.entity

import com.app.msgalarmclock.data.db.entity.ContactEntity
import com.app.msgalarmclock.presentation.model.ContactModel

object ContactEntityMapper {

    fun map(model: ContactModel): ContactEntity = with(model) {
        return ContactEntity(id ?: 0, name.orEmpty(), number.orEmpty())
    }
}