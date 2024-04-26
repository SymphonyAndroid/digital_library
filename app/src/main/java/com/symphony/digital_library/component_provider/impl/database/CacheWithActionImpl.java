package com.symphony.digital_library.component_provider.impl.database;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.CacheWithAction;
import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Consumer;
import java.util.function.Function;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CacheWithActionImpl<D extends BaseDao<R>, R> implements CacheWithAction<D, R> {


    @NonNull private final D dao;

    public CacheWithActionImpl(@NonNull D dao) {
        this.dao = dao;
    }

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @Override
    public <T> Single<T> single(@NonNull Function<D, T> function) {
        return Single
                .fromCallable(() -> function.apply(dao))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }

    @Override
    public Completable completable(@NonNull Consumer<D> function) {
        return Completable
                .fromAction(() -> function.accept(dao))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }
}
