package com.moham.contacts.features.addcontact.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moham.contacts.databinding.ActivityAddContactBinding
import com.moham.contacts.features.addcontact.viewmodel.AddContactViewModel
import com.moham.contacts.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactActivity : AppCompatActivity() {
    private lateinit var view: ActivityAddContactBinding
    private val viewModel: AddContactViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(view.root)

        clicks()
        setupObservers()

    }

    private fun clicks() {
        view.btnSave.setOnClickListener {
            val name = view.etName.text.toString()
            val phone = view.etPhone.text.toString()
            viewModel.addContact(name, phone)
        }
        view.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObservers() {
        viewModel.contactAdded.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    view.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Contact Added Successfully", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                Resource.Status.ERROR -> {
                    view.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    view.progressBar.visibility = View.VISIBLE
                }


            }
        }
    }
}