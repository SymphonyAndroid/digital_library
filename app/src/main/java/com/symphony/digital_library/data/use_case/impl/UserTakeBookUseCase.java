package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import io.reactivex.Completable;
import kotlin.Pair;

/**
 * Клас {@code UserTakeBookUseCase} реалізує сценарій, коли користувач бере книгу.
 * <p>
 * У цьому сценарії додається новий запис про користувача (якщо його немає у базі),
 * а також створюється зв'язок між користувачем і книгою.
 * Операція виконується асинхронно, з використанням компонентів бази даних.
 * </p>
 */
public class UserTakeBookUseCase implements UseCaseParams<Pair<User, Book>, Completable> {

    /**
     * Додає користувача до бази даних та встановлює зв'язок між користувачем і книгою, щоб відобразити
     * факт того, що користувач узяв книгу.
     *
     * @param item Пара значень {@link Pair}, що містить об'єкт {@link User} (користувач) та {@link Book} (книга),
     *             для якої потрібно створити зв'язок.
     * @return Асинхронний об'єкт {@code Completable}, що сигналізує про успішне виконання операції.
     *         У разі виникнення помилки {@code Completable} передає відповідний сигнал помилки.
     */
    @Override
    public Completable invoke(Pair<User, Book> item) {
        return addUser(item.getFirst()).concatWith(insertBook(item.getSecond().getId(), item.getFirst().getName()));
    }

    /**
     * Додає користувача до бази даних, якщо його ще немає.
     *
     * @param user Об'єкт {@link User}, який необхідно додати до бази даних.
     * @return Асинхронний об'єкт {@code Completable}, що сигналізує про завершення операції додавання.
     */
    private Completable addUser(User user) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .completable(userDao -> userDao.insert(user));
    }

    /**
     * Створює зв'язок між користувачем і книгою у базі даних.
     *
     * @param id   Ідентифікатор книги.
     * @param name Ім'я користувача.
     * @return Асинхронний об'єкт {@code Completable}, що сигналізує про завершення операції створення зв'язку.
     */
    private Completable insertBook(String id, String name) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserBookDao)
                .completable(dao -> dao.insert(new UserBookCrossRef(id, name)));
    }
}