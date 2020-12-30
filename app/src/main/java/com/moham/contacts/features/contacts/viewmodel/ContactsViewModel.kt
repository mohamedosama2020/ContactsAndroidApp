package com.moham.contacts.features.contacts.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.moham.contacts.model.entities.Contact
import com.moham.contacts.model.repo.Repository
import com.moham.contacts.utils.Resource
import com.moham.contacts.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.launch

class ContactsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _contacts = MutableLiveData<Resource<List<Contact>>>()
    val contacts: LiveData<Resource<List<Contact>>> = _contacts

    private val _isDeleted = MutableLiveData<Resource<Boolean>>()
    val isDeleted: LiveData<Resource<Boolean>> = _isDeleted


    fun getContacts() {
        viewModelScope.launch {
            _contacts.value = Resource.loading()
           val response =  repository.getContacts()
            if (response.status == SUCCESS){
                _contacts.value = response
            }else{
                _contacts.value = Resource.error(response.message)
            }
        }
    }

    fun deleteContact(idPhone:String) {
        viewModelScope.launch {
            _isDeleted.value = Resource.loading()
           val response =  repository.deleteContact(idPhone)
            if (response.status == SUCCESS){
                _isDeleted.value = response
            }else{
                _isDeleted.value = Resource.error(response.message)
            }
        }
    }

}
