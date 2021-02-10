package com.app.msgalarmclock.presentation.model

import java.io.Serializable

data class ContactModel(
    val id: Int? = null,
    val name: String? = null,
    val number: String? = null
): Serializable