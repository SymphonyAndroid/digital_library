package com.symphony.digital_library.component_provider.impl.database;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.Cache;
import com.symphony.digital_library.data.database.dao.BaseDao;
import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.database.dao.UserDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CacheImpl<T extends BaseDao<R> , R> implements Cache<R> {

    private final T dao;

    public CacheImpl(T dao) {
        this.dao = dao;
    }

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @Override
    public Single<List<R>> getAll() {
        return Single
                .fromCallable(dao::getAll)
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }

    @Override
    public Completable insert(R item) {
        return Completable
                .fromAction(() -> dao.insert(item))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }
    @Override
    public Completable insertAll(List<R> list) {
        return Completable
                .fromAction(() -> dao.insert(list))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().ui());
    }

    @Override
    public Single<List<R>> findByQuery(String query) {
        return Single
                .fromCallable(() -> dao.findByQuery(query))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }



//    private Single<List<User>> test2() {
//        if (dao instanceof UserDao) {
//            return Single
//                    .fromCallable(() -> ((UserDao) dao).test())
//                    .subscribeOn(getSchedulers().db())
//                    .observeOn(getSchedulers().io());
//        }
//
//        else return Single.error(new Throwable());
//    }
}
