package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.entity.Book;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.*;

/**
 * Інтерфейс API для взаємодії з віддаленим сервером додатку.
 * Забезпечує виклики для роботи з ресурсами, такими як книги.
 */
public interface Api {

    /**
     * Отримує список книг з сервера.
     *
     * Ця операція виконує HTTP POST-запит за вказаним маршрутом "/login".
     *
     * @return {@link Single} об'єкт, що емінує список {@link Book}.
     */
    @POST("/login")
    Single<List<Book>> getBooks();
}