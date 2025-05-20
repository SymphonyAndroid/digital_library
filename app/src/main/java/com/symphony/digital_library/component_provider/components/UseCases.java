package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kotlin.Pair;

/**
 * Інтерфейс, який визначає набір сценаріїв використання (use cases) для роботи з
 * основними сутностями системи цифрової бібліотеки.
 */
public interface UseCases {

    /**
     * Отримує сценарій для завантаження всіх книг із бази даних.
     *
     * @return {@link UseCase}, який забезпечує виконання запиту та повертає {@link Single} зі списком книг.
     */
    UseCase<Single<List<Book>>> getAllBooks();

    /**
     * Отримує сценарій для завантаження всіх книг із мережі.
     *
     * @return {@link UseCase}, який забезпечує виконання запиту та повертає {@link Single} зі списком книг.
     */
    UseCase<Single<List<Book>>> getAllBooksFromNetwork();

    /**
     * Отримує сценарій для заповнення бази даних.
     *
     * @return {@link UseCase}, який забезпечує виконання асинхронної операції (без результату) через {@link Completable}.
     */
    UseCase<Completable> fillingDatabase();

    /**
     * Отримує сценарій для пошуку книг за заданим рядком пошуку.
     *
     * @return {@link UseCaseParams}, який приймає рядок пошуку як параметр і повертає {@link Single} зі списком книг.
     */
    UseCaseParams<String, Single<List<Book>>> findBooks();

    /**
     * Отримує сценарій для пошуку користувачів за іменем.
     *
     * @return {@link UseCaseParams}, який приймає рядок пошуку як параметр і повертає {@link Single} зі списком користувачів.
     */
    UseCaseParams<String, Single<List<User>>> findUsersByName();

    /**
     * Отримує сценарій для пошуку одного користувача за іменем.
     *
     * @return {@link UseCaseParams}, який приймає рядок пошуку як параметр і повертає {@link Single} із користувачем.
     */
    UseCaseParams<String, Single<User>> findUserByName();

    /**
     * Отримує сценарій для завершення дії, коли користувач бере книгу.
     *
     * @return {@link UseCaseParams}, який приймає {@link Pair} з користувача та книги, і повертає {@link Completable}.
     */
    UseCaseParams<Pair<User, Book>, Completable> bookTaken();

    /**
     * Отримує сценарій для пошуку книг, які пов'язані із заданим користувачем.
     *
     * @return {@link UseCaseParams}, який приймає користувача як параметр і повертає {@link Single} зі списком книг.
     */
    UseCaseParams<User, Single<List<Book>>> booksByUser();

    /**
     * Отримує сценарій для виконання дії повернення книги користувачем.
     *
     * @return {@link UseCaseParams}, який приймає {@link UserBookCrossRef} як параметр і повертає {@link Completable}.
     */
    UseCaseParams<UserBookCrossRef, Completable> returnBook();
}