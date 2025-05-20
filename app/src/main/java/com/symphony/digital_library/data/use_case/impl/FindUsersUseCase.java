package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Single;

/**
 * Клас {@code FindUsersUseCase} реалізує сценарій пошуку користувачів у базі даних
 * за частковим співпадінням імені.
 * <p>
 * Цей сценарій приймає строку пошукового запиту та повертає список користувачів, імена
 * яких збігаються (повністю або частково) із заданим запитом. Результати обробляються
 * асинхронно за допомогою RxJava.
 * </p>
 */
public class FindUsersUseCase implements UseCaseParams<String, Single<List<User>>> {

    /**
     * Здійснює пошук користувачів у базі даних на основі заданої строки.
     *
     * @param item Пошукова строка (запит), яка використовується для пошуку користувачів.
     *             Запит автоматично обгортається у символи `%` для забезпечення часткового співпадіння.
     * @return Асинхронний об'єкт {@code Single<List<User>>}, що містить список знайдених користувачів.
     *         Якщо користувачі не знайдені, повертається порожній список.
     */
    @Override
    public Single<List<User>> invoke(String item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(userDao -> userDao.findUsersByName("%" + item + "%"));
    }

}