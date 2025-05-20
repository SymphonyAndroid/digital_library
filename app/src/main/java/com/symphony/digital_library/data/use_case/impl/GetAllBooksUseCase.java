package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;

import java.util.List;

import io.reactivex.Single;

/**
 * Клас {@code GetAllBooksUseCase} реалізує сценарій отримання всіх книг із бази даних.
 * <p>
 * Цей сценарій забезпечує доступ до всіх записів про книги через асинхронний запит.
 * Результат повертається у вигляді списку об'єктів {@link Book}.
 * </p>
 */
public class GetAllBooksUseCase implements UseCase<Single<List<Book>>> {

    /**
     * Виконує запит для отримання всіх книг із бази даних.
     *
     * @return Асинхронний об'єкт {@code Single<List<Book>>}, що містить список усіх збережених книг.
     *         Якщо книг у базі даних немає, повертається порожній список.
     */
    @Override
    public Single<List<Book>> invoke() {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getBookDao)
                .single(BookDao::getAll);
    }
}