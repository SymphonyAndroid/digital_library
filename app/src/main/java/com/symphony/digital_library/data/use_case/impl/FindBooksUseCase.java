package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Single;

/**
 * Клас {@code FindBooksUseCase} реалізує сценарій використання для пошуку книг у базі даних.
 * <p>
 * Цей сценарій приймає текстовий параметр запиту для пошуку книг, назва або атрибути яких
 * частково збігаються із заданою строкою. Результати повертаються у вигляді асинхронного
 * списку об'єктів {@link Book}.
 * </p>
 */
public class FindBooksUseCase implements UseCaseParams<String, Single<List<Book>>> {

    /**
     * Виконує сценарій пошуку книг на основі текстового запиту.
     *
     * @param query Строка пошукового запиту, яка використовується для пошуку книг у базі даних.
     *              Для забезпечення часткового співпадіння запит автоматично обгортається у символи `%`.
     * @return Об'єкт {@code Single<List<Book>>}, що містить список знайдених книг.
     *         Якщо книги не знайдено, повертається порожній список.
     */
    @Override
    public Single<List<Book>> invoke(String query) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getBookDao)
                .single(bookDao -> bookDao.findByQuery("%" + query + "%"));
    }

}