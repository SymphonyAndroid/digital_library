package com.symphony.digital_library.component_provider.components;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface Cache<T> {

    Single<List<T>> getAll();
    Completable insert(T item);
    Completable insertAll(List<T> list);

    Single<List<T>> findByQuery(String query);

}
