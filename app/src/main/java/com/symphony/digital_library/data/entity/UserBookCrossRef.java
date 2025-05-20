package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Клас {@code UserBookCrossRef} представляє зв'язок "багато-до-багатьох" між книгами та користувачами.
 * <p>
 * Ця сутність використовується для створення таблиці посилань у базі даних, яка поєднує книги
 * ({@code bookId}) та користувачів ({@code userId}). Визначено комбінований первинний ключ,
 * що складається з ідентифікаторів книги та користувача.
 * </p>
 */
@Entity(primaryKeys = {"bookId", "userId"})
public class UserBookCrossRef {

    /**
     * Ідентифікатор книги у зв'язку.
     */
    @NonNull
    private final String bookId;

    /**
     * Ідентифікатор користувача у зв'язку.
     */
    @NonNull
    private final String userId;

    /**
     * Конструктор для створення об'єкта {@code UserBookCrossRef}.
     *
     * @param bookId Унікальний ідентифікатор книги.
     * @param userId Унікальний ідентифікатор користувача.
     * @throws NullPointerException Якщо будь-який з параметрів є {@code null}.
     */
    public UserBookCrossRef(@NonNull String bookId, @NonNull String userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    /**
     * Повертає ідентифікатор книги.
     *
     * @return Унікальний ідентифікатор книги.
     */
    @NonNull
    public String getBookId() {
        return bookId;
    }

    /**
     * Повертає ідентифікатор користувача.
     *
     * @return Унікальний ідентифікатор користувача.
     */
    @NonNull
    public String getUserId() {
        return userId;
    }
}