package com.moham.contacts.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moham.contacts.model.remote.RemoteDataSource
import com.moham.contacts.model.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideFireStore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideFireBaseAuth() = Firebase.auth


    @Singleton
    @Provides
    fun provideRemoteDataSource(auth: FirebaseAuth,firestore: FirebaseFirestore) = RemoteDataSource(auth,firestore)


    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource) =
        Repository(remoteDataSource)
}