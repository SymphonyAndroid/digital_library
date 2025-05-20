package com.symphony.digital_library.component_provider.components;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Consumer;
import java.util.function.Function;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Генеричний інтерфейс для роботи з кешем і виконанням операцій за допомогою DAO (Data Access Object).
 *
 * @param <D> тип об'єкта DAO, який розширює {@link BaseDao}.
 * @param <R> тип сутності, з якою працює DAO.
 */
public interface CacheWithAction<D extends BaseDao<R>, R> {

    /**
     * Виконує операцію, що повертає результат, використовуючи передану функцію та DAO.
     *
     * @param function функція, яка приймає DAO як аргумент і повертає результат типу {@code T}.
     * @param <T> тип результату, що повертається функцією.
     * @return {@link Single}, який емінує результат виконання функції.
     */
    <T> Single<T> single(@NonNull Function<D, T> function);

    /**
     * Виконує операцію, яка не повертає результат, але виконується за допомогою переданого DAO.
     *
     * @param function споживач ({@link Consumer}), який приймає DAO для виконання певної дії.
     * @return {@link Completable}, який сигналізує про завершення операції.
     */
    Completable completable(@NonNull Consumer<D> function);
}
