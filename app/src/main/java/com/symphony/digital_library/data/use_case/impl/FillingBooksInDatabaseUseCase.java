package com.symphony.digital_library.data.use_case.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.symphony.digital_library.R;
import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Клас {@code FillingBooksInDatabaseUseCase} відповідає за заповнення бази даних книгами з локального JSON-файлу.
 * <p>
 * Основним завданням є зчитування даних з ресурсу в JSON-форматі, перетворення їх у список об'єктів {@link Book}
 * і вставка цих об'єктів у базу даних через DAO.
 * </p>
 * <p>
 * Використовується асинхронний підхід через RxJava для забезпечення невитратності основного потоку.
 * </p>
 */
public class FillingBooksInDatabaseUseCase implements UseCase<Completable> {

    /**
     * Контекст додатка, необхідний для доступу до ресурсів.
     */
    private final Context context;

    /**
     * Створює новий екземпляр {@code FillingBooksInDatabaseUseCase}.
     *
     * @param context Контекст додатка для доступу до ресурсів, таких як файл JSON.
     */
    public FillingBooksInDatabaseUseCase(Context context) {
        this.context = context;
    }

    /**
     * Виконує сценарій заповнення бази даних книгами.
     * <p>
     * Метод зчитує JSON-файл, перетворює його в список об'єктів {@link Book},
     * а потім передає їх у базу даних. Усі дії виконуються асинхронно.
     * </p>
     *
     * @return Об'єкт {@code Completable}, який завершується, коли всі книги успішно додані в базу даних.
     */
    @Override
    public Completable invoke() {
        return Single.fromCallable(() -> {
                    InputStream is = context.getResources().openRawResource(R.raw.base_books);
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    return new Gson().fromJson(reader, new TypeToken<List<Book>>() { });
                })
                .subscribeOn(getProvider().getSchedulers().io())
                .flatMapCompletable(this::insert);
    }

    /**
     * Вставляє список книг у базу даних.
     * <p>
     * Цей метод працює з DAO для здійснення операції вставки.
     * </p>
     *
     * @param books Список об'єктів {@link Book}, які потрібно додати в базу даних.
     * @return Об'єкт {@code Completable}, який завершує вставку.
     */
    private Completable insert(List<Book> books) {
        return getProvider()
                .getCacheWithAction(Database::getBookDao)
                .completable(bookDao -> bookDao.insert(books));
    }

    /**
     * Повертає екземпляр {@link ComponentProvider} для отримання необхідних об'єктів.
     *
     * @return Екземпляр {@code ComponentProvider}.
     */
    private ComponentProvider getProvider() {
        return ComponentProvider.Companion.getInstance();
    }

}