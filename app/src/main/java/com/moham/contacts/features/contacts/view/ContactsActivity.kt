package com.moham.contacts.features.contacts.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moham.contacts.databinding.ActivityContactsBinding
import com.moham.contacts.features.addcontact.view.AddContactActivity
import com.moham.contacts.features.contacts.adapter.ContactsAdapter
import com.moham.contacts.features.contacts.adapter.SwipeAction
import com.moham.contacts.features.contacts.viewmodel.ContactsViewModel
import com.moham.contacts.model.entities.Contact
import com.moham.contacts.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {

    private lateinit var view: ActivityContactsBinding
    private val viewModel: ContactsViewModel by viewModels()
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(view.root)

        initUiComponents()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.contacts.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    view.progressBar.visibility = View.INVISIBLE
                    handleSuccessResponse(it.data)
                }
                Resource.Status.ERROR -> {
                    view.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    view.progressBar.visibility = View.VISIBLE
                }


            }
        }

        viewModel.isDeleted.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    view.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()
                    callData()
                }
                Resource.Status.ERROR -> {
                    view.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    view.progressBar.visibility = View.VISIBLE
                }


            }
        }
    }

    private fun handleSuccessResponse(contacts: List<Contact>?) {
        if (contacts.isNullOrEmpty()) {
            view.emptyView.visibility = View.VISIBLE
        } else {
            view.emptyView.visibility = View.GONE
            contactsAdapter.setContacts(contacts)
        }

    }

    private fun initUiComponents() {

        contactsAdapter = ContactsAdapter(this) { contact, action ->
            when (action) {
                SwipeAction.Call -> callContact(contact.phone)
                SwipeAction.Delete -> deleteContact(contact.phone)
            }
        }

        view.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }

        view.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@ContactsActivity)
            adapter = contactsAdapter
        }

        callData()
    }

    private fun callContact(phone: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
    }

    private fun deleteContact(idPhone: String) {
        viewModel.deleteContact(idPhone)
        viewModel.getContacts()
    }

    private fun callData() {
        viewModel.getContacts()
    }

    override fun onResume() {
        super.onResume()
        callData()
    }
}