package com.moham.contacts.model.repo;

import com.moham.contacts.model.remote.RemoteDataSource;

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
public final class Repository_Factory implements Factory<Repository> {
    private final Provider<RemoteDataSource> remoteDataSourceProvider;

    public Repository_Factory(Provider<RemoteDataSource> remoteDataSourceProvider) {
        this.remoteDataSourceProvider = remoteDataSourceProvider;
    }

    public static Repository_Factory create(Provider<RemoteDataSource> remoteDataSourceProvider) {
        return new Repository_Factory(remoteDataSourceProvider);
    }

    public static Repository newInstance(RemoteDataSource remoteDataSource) {
        return new Repository(remoteDataSource);
    }

    @Override
    public Repository get() {
        return newInstance(remoteDataSourceProvider.get());
    }
}
