package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.database.dao.UserDao;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCase;

import java.util.List;

import io.reactivex.Single;

/**
 * Клас {@code GetAllUsersUseCase} реалізує сценарій отримання всіх користувачів із бази даних.
 * <p>
 * Цей сценарій забезпечує доступ до всіх записів про користувачів через асинхронний запит.
 * Результат повертається у вигляді списку об'єктів {@link User}.
 * </p>
 */
public class GetAllUsersUseCase implements UseCase<Single<List<User>>> {

    /**
     * Виконує запит для отримання всіх користувачів із бази даних.
     *
     * @return Асинхронний об'єкт {@code Single<List<User>>}, що містить список усіх користувачів.
     *         Якщо користувачі в базі даних відсутні, повертається порожній список.
     */
    @Override
    public Single<List<User>> invoke() {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(UserDao::getAll);
    }
}