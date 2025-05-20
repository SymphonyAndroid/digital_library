package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.UserBookCrossRef;

import java.util.List;

/**
 * DAO (Data Access Object) для роботи із сутністю {@link UserBookCrossRef}.
 * <p>
 * Цей інтерфейс дозволяє виконувати операції з таблицею, яка зберігає зв'язки між
 * користувачами та книгами в базі даних.
 * Наслідує базову функціональність із {@link BaseDao}.
 * </p>
 */
@Dao
public interface UserBookCrossRefDao extends BaseDao<UserBookCrossRef> {

    /**
     * Отримує всі записи із таблиці "UserBookCrossRef".
     * <p>
     * Метод перевизначає базовий метод {@link BaseDao#getAll()} та виконує SQL-запит
     * для отримання повного списку зв'язків між користувачами та книгами.
     * </p>
     *
     * @return Список усіх записів зв'язків {@link UserBookCrossRef}, які збережені у базі даних.
     */
    @Override
    @Query("SELECT * FROM UserBookCrossRef")
    List<UserBookCrossRef> getAll();

}