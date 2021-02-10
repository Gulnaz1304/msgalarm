package com.app.msgalarmclock.presentation.dialog

import androidx.annotation.StringRes
import java.io.Serializable

class ConfirmDialogParams(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val negativeButtonText: Int,
    @StringRes val positiveButtonText: Int
) : Serializable