package com.moham.contacts.features.contactinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moham.contacts.databinding.ActivityContactInfotBinding
import com.moham.contacts.model.entities.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactInfoActivity : AppCompatActivity() {
    private lateinit var view: ActivityContactInfotBinding

    companion object {
        const val INTENT_EXTRA = "contact"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityContactInfotBinding.inflate(layoutInflater)
        setContentView(view.root)

        initUi()
        clicks()
    }

    private fun initUi() {
        val contact = intent.getParcelableExtra<Contact>(INTENT_EXTRA)
        view.tvName.text = contact?.name
        view.tvPhoneNumber.text = contact?.phone
    }

    private fun clicks() {
        view.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}