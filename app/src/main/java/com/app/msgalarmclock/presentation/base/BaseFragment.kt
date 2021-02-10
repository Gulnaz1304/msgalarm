package com.app.msgalarmclock.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.msgalarmclock.R
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    open var toolbarTitle: Int = R.string.app_name

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? BaseActivity)?.supportActionBar?.setTitle(toolbarTitle)
    }

    protected fun navigateTo(fragment: BaseFragment) {
        (activity as? BaseActivity)?.navigateTo(fragment)
    }

    protected fun finish() {
        activity?.supportFragmentManager?.popBackStack()
    }
}