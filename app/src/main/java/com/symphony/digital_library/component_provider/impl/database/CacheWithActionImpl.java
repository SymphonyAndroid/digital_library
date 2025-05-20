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

/**
 * Реалізація інтерфейсу {@link CacheWithAction}, яка забезпечує виконання операцій
 * з базою даних через DAO з використанням реактивного програмування (RxJava).
 *
 * @param <D> Тип об'єкта DAO, через який працюватиме кеш.
 * @param <R> Тип об'єкта сутності, яка зберігається у базі даних.
 */
public class CacheWithActionImpl<D extends BaseDao<R>, R> implements CacheWithAction<D, R> {

    /**
     * DAO для виконання операцій із базою даних.
     */
    @NonNull private final D dao;

    /**
     * Конструктор для ініціалізації DAO.
     *
     * @param dao Об'єкт DAO для виконання операцій з базою даних.
     */
    public CacheWithActionImpl(@NonNull D dao) {
        this.dao = dao;
    }

    /**
     * Отримує реалізацію {@link AppSchedulers} для визначення потоків виконання.
     *
     * @return Екземпляр {@link AppSchedulers}, який включає планувальники для базової роботи додатку.
     */
    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    /**
     * Виконує операцію, яка повертає одиничне значення з бази даних, на заданих потоках.
     *
     * @param <T>      Тип повертаємого результату.
     * @param function Функція, яка приймає DAO і повертає результат.
     * @return RxJava {@link Single} з результатом операції.
     */
    @Override
    public <T> Single<T> single(@NonNull Function<D, T> function) {
        return Single
                .fromCallable(() -> function.apply(dao))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }

    /**
     * Виконує операцію, яка не повертає результату, на заданих потоках.
     *
     * @param function Функція, яка приймає DAO і виконує операцію.
     * @return RxJava {@link Completable}, що сигналізує завершення операції.
     */
    @Override
    public Completable completable(@NonNull Consumer<D> function) {
        return Completable
                .fromAction(() -> function.accept(dao))
                .subscribeOn(getSchedulers().db())
                .observeOn(getSchedulers().io());
    }
}