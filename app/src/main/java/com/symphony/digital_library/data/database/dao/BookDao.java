package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.Book;

import java.util.List;

/**
 * DAO (Data Access Object) для роботи із сутністю {@link Book}.
 * <p>
 * Це інтерфейс для виконання операцій із таблицею "Book" у базі даних,
 * включно з базовими CRUD-операціями та спеціалізованими запитами.
 * Наслідує базову функціональність із {@link BaseDao}.
 */
@Dao
public interface BookDao extends BaseDao<Book> {

    /**
     * Отримує всі записи з таблиці "Book".
     * <p>
     * Метод перевизначає базовий метод {@link BaseDao#getAll()} та виконує SQL-запит
     * для отримання повного списку книг.
     * </p>
     *
     * @return Список усіх книг, які збережені у базі даних.
     */
    @Override
    @Query("SELECT * FROM Book")
    List<Book> getAll();

    /**
     * Шукає книги за запитом у назві або авторі.
     * <p>
     * Повертає список книг, які не були взяті користувачами (книги, яких немає у зв'язках "UserBookCrossRef"),
     * і де назва або автор відповідають заданому рядку пошуку.
     * </p>
     *
     * @param query Рядок для пошуку, що співпадає з назвою книги або її автором.
     * @return Список книг, які відповідають критеріям пошуку.
     */
    @Query("SELECT * FROM Book WHERE id NOT IN (SELECT bookId FROM UserBookCrossRef) AND (title LIKE :query OR author LIKE :query)")
    List<Book> findByQuery(String query);

}