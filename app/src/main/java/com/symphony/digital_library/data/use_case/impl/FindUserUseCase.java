package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import io.reactivex.Single;

/**
 * Клас {@code FindUserUseCase} реалізує сценарій пошуку конкретного користувача у базі даних.
 * <p>
 * Цей сценарій приймає строку пошукового запиту (ім'я користувача) та повертає знайденого
 * користувача у вигляді об'єкта {@link User}. Якщо користувача не знайдено, асинхронна операція
 * повертає помилку або спеціальний результат.
 * </p>
 */
public class FindUserUseCase implements UseCaseParams<String, Single<User>> {

    /**
     * Здійснює пошук користувача у базі даних за іменем.
     *
     * @param item Строка пошукового запиту, що представляє ім'я користувача, якого потрібно знайти.
     * @return Асинхронний об'єкт {@code Single<User>}, що містить знайденого користувача.
     *         У разі відсутності користувача може бути повернена помилка або інший спеціальний сигнал.
     */
    @Override
    public Single<User> invoke(String item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(userDao -> userDao.findUserByName(item));
    }

}