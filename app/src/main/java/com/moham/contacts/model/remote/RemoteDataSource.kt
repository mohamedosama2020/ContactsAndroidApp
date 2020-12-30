package com.moham.contacts.model.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.moham.contacts.model.entities.Contact
import com.moham.contacts.utils.Resource
import com.moham.contacts.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signIn(): Resource<FirebaseUser> {
        auth.signInAnonymously().await()
        return if (auth.currentUser != null) {
            Resource.success(auth.currentUser!!)
        } else {
            Resource.error("Login Error")
        }
    }

    suspend fun getContacts(): Resource<List<Contact>> =

        suspendCoroutine { continuation ->
            firestore.collection("users")
                .document("${auth.currentUser?.uid}")
                .collection("contacts").get().addOnCompleteListener {
                    continuation.resume(Resource.success(it.result!!.toObjects(Contact::class.java)))
                }.addOnFailureListener {
                    continuation.resume(Resource.error(it.localizedMessage))
                }
        }

    suspend fun addContact(name: String, idPhone: String): Resource<Boolean> {

        val isPhoneExists = isPhoneExist(idPhone)
        return if (isPhoneExists.status == SUCCESS) {
            suspendCoroutine { continuation ->
                firestore.collection("users").document("${auth.currentUser?.uid}")
                    .collection("contacts").document(idPhone)
                    .set(Contact(name, idPhone)).addOnCompleteListener {
                        continuation.resume(Resource.success(true))
                    }.addOnFailureListener {
                        continuation.resume(Resource.error(it.localizedMessage))
                    }
            }
        } else {
            isPhoneExists
        }
    }

    suspend fun deleteContact(idPhone: String): Resource<Boolean> =
        suspendCoroutine { continuation ->
            firestore.collection("users").document("${auth.currentUser?.uid}")
                .collection("contacts").document(idPhone)
                .delete().addOnCompleteListener {
                    continuation.resume(Resource.success(true))
                }.addOnFailureListener {
                    continuation.resume(Resource.error(it.localizedMessage))
                }
        }


    suspend fun isPhoneExist(idPhone: String): Resource<Boolean> {

        return try {
            val result = firestore.collection("users")
                .document("${auth.currentUser?.uid}")
                .collection("contacts").document(idPhone).get().await()
            if (result.exists()) {
                Resource.error("Phone Exists")
            } else {
                Resource.success(result.exists())
            }
        }catch(e:Exception){
            Resource.error("Error Occurred Please Check Your Connection")
        }
    }

}