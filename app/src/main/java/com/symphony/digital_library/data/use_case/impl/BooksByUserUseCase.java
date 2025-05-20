package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Клас {@code BooksByUserUseCase} реалізує сценарій використання, пов'язаний із отриманням списку книг,
 * що належать конкретному користувачу.
 * <p>
 * Цей клас використовує дженерик інтерфейс {@link UseCaseParams}, приймаючи користувача
 * {@link User} як вхідний параметр і повертаючи результат у вигляді {@code Single<List<Book>>}.
 * Реалізація забезпечує асинхронне отримання списку книг через DAO.
 * </p>
 */
public class BooksByUserUseCase implements UseCaseParams<User, Single<List<Book>>> {

    /**
     * Виконує сценарій використання для отримання списку книг, які належать користувачу.
     *
     * @param item Об'єкт {@link User}, для якого потрібно знайти книги.
     * @return Об'єкт {@code Single<List<Book>>}, що представляє результат операції, виконаної асинхронно.
     * Якщо у користувача немає жодної книги, повертається порожній список.
     */
    @Override
    public Single<List<Book>> invoke(User item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(dao -> dao.booksByUser(item.getName()))
                .map(it -> it.getBooks() == null ? new ArrayList<>() : it.getBooks());
    }

}