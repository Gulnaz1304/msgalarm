package com.app.msgalarmclock.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.msgalarmclock.R
import kotlinx.android.synthetic.main.dialog_confirm.*

class ConfirmDialogFragment : DialogFragment() {

    var onPositiveBtnClick: (() -> Unit)? = null
    var onNegativeBtnClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getSerializable(KEY_DIALOG_PARAMS) as? ConfirmDialogParams)?.apply {
            confirmTitle.setText(title)
            confirmDescription.setText(description)
            confirmButtonPositive.setText(positiveButtonText)
            confirmButtonNegative.setText(negativeButtonText)
        }

        confirmButtonPositive.setOnClickListener {
            onPositiveBtnClick?.invoke()
            dismiss()
        }

        confirmButtonNegative.setOnClickListener {
            onNegativeBtnClick?.invoke()
            dismiss()
        }
    }


    companion object {
        private const val KEY_DIALOG_PARAMS = "KEY_DIALOG_PARAMS"

        fun getInstance(params: ConfirmDialogParams) = ConfirmDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_DIALOG_PARAMS, params)
            }
        }
    }
}