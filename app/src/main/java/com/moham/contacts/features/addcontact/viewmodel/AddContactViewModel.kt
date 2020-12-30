package com.moham.contacts.features.addcontact.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.moham.contacts.model.repo.Repository
import com.moham.contacts.utils.Resource
import com.moham.contacts.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.launch

class AddContactViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _contactAdded = MutableLiveData<Resource<Boolean>>()
    val contactAdded: LiveData<Resource<Boolean>> = _contactAdded


    fun addContact(name:String,idPhone:String) {
        viewModelScope.launch {
            _contactAdded.value = Resource.loading()
            val response =  repository.addContact(name,idPhone)
            if (response.status == SUCCESS){
                _contactAdded.value = response
                Log.d("ContactsView: ","${response.data}")
            }else{
                _contactAdded.value = Resource.error(response.message)
                Log.d("ContactsView: ","${response.message}")
            }
        }
    }
}
