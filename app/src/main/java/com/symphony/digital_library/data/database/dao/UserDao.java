package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserTakenBook;

import java.util.List;

/**
 * DAO (Data Access Object) для роботи із сутністю {@link User}.
 * <p>
 * Інтерфейс використовується для виконання операцій з таблицею "User" у базі даних,
 * зокрема для базових CRUD-операцій та специфічних запитів, пов'язаних із користувачами.
 * </p>
 * Наслідує основну функціональність із {@link BaseDao}.
 */
@Dao
public interface UserDao extends BaseDao<User> {

    /**
     * Отримує всі записи з таблиці "User".
     * <p>
     * Метод перевизначає базову функцію {@link BaseDao#getAll()} для отримання повного списку користувачів.
     * </p>
     *
     * @return Список усіх користувачів, збережених у базі даних.
     */
    @Override
    @Query("SELECT * FROM User")
    List<User> getAll();

    /**
     * Шукає користувачів, чиє ім'я частково збігається із заданим запитом.
     *
     * @param name Рядок або частина рядка для пошуку імен користувачів.
     * @return Список користувачів, чиї імена відповідають заданому запиту.
     */
    @Query("SELECT * FROM User WHERE name LIKE :name")
    List<User> findUsersByName(String name);

    /**
     * Шукає користувача за повним збігом імені.
     *
     * @param name Ім'я користувача, яке необхідно знайти.
     * @return Об'єкт {@link User}, чия назва співпадає із заданим ім'ям,
     * або {@code null}, якщо користувача не знайдено.
     */
    @Query("SELECT * FROM User WHERE name = :name")
    User findUserByName(String name);

    /**
     * Отримує інформацію про книги, взяті користувачем.
     * <p>
     * Використовує ім'я користувача для пошуку даних про книги, які зараз позначені як взяті цим користувачем.
     * </p>
     *
     * @param name Ім'я користувача, для якого потрібно знайти книги.
     * @return Об'єкт {@link UserTakenBook}, що містить інформацію про взяті книги,
     * або {@code null}, якщо дані користувача або книги не знайдено.
     */
    @Query("SELECT * FROM User WHERE name = :name")
    UserTakenBook booksByUser(String name);

}