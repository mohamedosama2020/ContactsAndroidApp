package com.moham.contacts.model.repo


import com.moham.contacts.model.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun signIn() = remoteDataSource.signIn()
    suspend fun getContacts() = remoteDataSource.getContacts()
    suspend fun addContact(name: String, idPhone: String) =
        remoteDataSource.addContact(name, idPhone)

    suspend fun deleteContact(idPhone: String) = remoteDataSource.deleteContact(idPhone)
}