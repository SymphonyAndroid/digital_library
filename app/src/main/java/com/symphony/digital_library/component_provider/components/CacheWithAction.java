package com.symphony.digital_library.component_provider.components;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Consumer;
import java.util.function.Function;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CacheWithAction<D extends BaseDao<R>, R> {
    <T> Single<T> single(@NonNull Function<D, T> function);
    Completable completable(@NonNull Consumer<D> function);
}
