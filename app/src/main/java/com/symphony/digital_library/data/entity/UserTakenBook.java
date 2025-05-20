package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * Клас {@code UserTakenBook} представляє модель, яка з'єднує користувача з книгами, які він взяв.
 * <p>
 * Ця модель використовується для відображення у базі даних зв'язку "багато-до-багатьох" між сутностями
 * {@link User} та {@link Book} через проміжну таблицю {@link UserBookCrossRef}.
 * </p>
 */
public class UserTakenBook {

    /**
     * Об'єкт користувача, який взяв книги.
     */
    @NonNull
    @Embedded
    private User user;

    /**
     * Список книг, які взяв користувач.
     * <p>
     * Зв'язок між користувачем та книгами визначений за допомогою анотації {@link Relation},
     * яка використовує таблицю {@link UserBookCrossRef}.
     * </p>
     */
    @Relation(
            parentColumn = "name",
            entityColumn = "id",
            associateBy = @Junction(
                    value = UserBookCrossRef.class,
                    parentColumn = "userId",
                    entityColumn = "bookId"
            )
    )
    private List<Book> books;

    /**
     * Конструктор для створення об'єкта {@code UserTakenBook}.
     *
     * @param user  Користувач, який взяв книги.
     * @param books Список книг, які взяв цей користувач.
     */
    public UserTakenBook(@NonNull User user, List<Book> books) {
        this.user = user;
        this.books = books;
    }

    /**
     * Повертає користувача, який взяв книги.
     *
     * @return Об'єкт {@link User}, який представляє користувача.
     */
    @NonNull
    public User getUser() {
        return user;
    }

    /**
     * Повертає список книг, які взяв користувач.
     *
     * @return Список об'єктів {@link Book}, які взяв користувач.
     */
    public List<Book> getBooks() {
        return books;
    }
}