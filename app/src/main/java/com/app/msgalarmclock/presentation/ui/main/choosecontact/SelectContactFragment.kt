package com.app.msgalarmclock.presentation.ui.main.choosecontact

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.msgalarmclock.App
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.base.BaseFragment
import com.app.msgalarmclock.presentation.extension.injectViewModel
import com.app.msgalarmclock.presentation.extension.showToast
import kotlinx.android.synthetic.main.fragment_select_contact.*

class SelectContactFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_select_contact

    override var toolbarTitle: Int = R.string.contacts

    lateinit var viewModel: SelectContactViewModel

    private val adapter = ContactListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        viewModel.actionError.observe(viewLifecycleOwner) {
            context?.showToast(R.string.contact_select_error)
        }

        viewModel.actionSuccess.observe(viewLifecycleOwner) { contact ->
            setFragmentResult(
                REQUEST_CODE_CONTACT,
                bundleOf(KEY_CONTACT_MODEL to contact)
            )
            finish()
        }

        adapter.items = viewModel.contacts
        arguments?.getInt(KEY_CONTACT_ID)?.let {
            adapter.setCheckedItem(it)
        }

        listContacts.layoutManager = LinearLayoutManager(context)
        listContacts.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save) {
            viewModel.selectContact(adapter.getCheckedItem())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    companion object {
        private const val KEY_CONTACT_ID = "KEY_CONTACT_ID"
        const val REQUEST_CODE_CONTACT = "REQUEST_CODE_CONTACT"
        const val KEY_CONTACT_MODEL = "KEY_CONTACT_MODEL"

        fun getInstance(contactId: Int? = null) = SelectContactFragment().apply {
            contactId?.let {
                arguments = Bundle().apply {
                    putInt(KEY_CONTACT_ID, it)
                }
            }
        }
    }
}