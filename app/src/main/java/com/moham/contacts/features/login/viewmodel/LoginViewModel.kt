package com.moham.contacts.features.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.moham.contacts.model.repo.Repository
import com.moham.contacts.utils.Resource
import com.moham.contacts.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _values = MutableLiveData<Resource<FirebaseUser>>()
    val values: LiveData<Resource<FirebaseUser>> = _values


    fun signIn() {
        viewModelScope.launch {
            _values.value = Resource.loading()
           val response =  repository.signIn()
            if (response.status == SUCCESS){
                _values.value = response
            }else{
                _values.value = Resource.error(response.message)
            }
        }
    }

}
