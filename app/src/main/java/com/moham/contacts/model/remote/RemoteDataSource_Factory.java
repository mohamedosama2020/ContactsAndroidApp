package com.moham.contacts.model.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Generated;
import javax.inject.Provider;

import dagger.internal.Factory;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://dagger.dev"
)
@SuppressWarnings({
        "unchecked",
        "rawtypes"
})
public final class RemoteDataSource_Factory implements Factory<RemoteDataSource> {
    private final Provider<FirebaseAuth> authProvider;

    private final Provider<FirebaseFirestore> firestoreProvider;

    public RemoteDataSource_Factory(Provider<FirebaseAuth> authProvider,
                                    Provider<FirebaseFirestore> firestoreProvider) {
        this.authProvider = authProvider;
        this.firestoreProvider = firestoreProvider;
    }

    public static RemoteDataSource_Factory create(Provider<FirebaseAuth> authProvider,
                                                  Provider<FirebaseFirestore> firestoreProvider) {
        return new RemoteDataSource_Factory(authProvider, firestoreProvider);
    }

    public static RemoteDataSource newInstance(FirebaseAuth auth, FirebaseFirestore firestore) {
        return new RemoteDataSource(auth, firestore);
    }

    @Override
    public RemoteDataSource get() {
        return newInstance(authProvider.get(), firestoreProvider.get());
    }
}
