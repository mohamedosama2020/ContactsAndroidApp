package com.moham.contacts.features.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.moham.contacts.databinding.ActivityLoginBinding
import com.moham.contacts.features.contacts.view.ContactsActivity
import com.moham.contacts.utils.Resource.Status.*
import com.moham.contacts.features.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var view: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)


        if (auth.currentUser != null) {
            startActivity(Intent(this,ContactsActivity::class.java))
            finish()
        } else {
            setupObservers()
        }
        clicks()



//        //Check If Exists
//        db.collection("users")
//            .document("${auth.currentUser?.uid}")
//            .collection("contacts").document("01204630555")
//            .addSnapshotListener { value, error ->
//                if (value?.exists() == true) {
//                    Log.d("MainActivity isExists", "True")
//                } else
//                    Log.d("MainActivity isExists", "False")
//
//            }
//

    }

    private fun clicks(){
        view.btnSignIn.setOnClickListener {
            viewModel.signIn()
        }
    }

    private fun setupObservers() {
        viewModel.values.observe(this) {
            when (it.status) {
                SUCCESS -> {
                    view.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ContactsActivity::class.java))
                    finish()
                }
                ERROR -> {
                    view.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

                LOADING ->{
                    view.progressBar.visibility = View.VISIBLE
                }


            }
        }
    }
}