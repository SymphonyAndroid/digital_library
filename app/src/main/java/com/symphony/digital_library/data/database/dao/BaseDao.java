package com.symphony.digital_library.data.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

/**
 * Базовий DAO (Data Access Object) інтерфейс для роботи з базою даних.
 * <p>
 * Забезпечує основні CRUD-операції (створення, читання, оновлення, видалення)
 * для роботи зі збереженими об'єктами в базі даних.
 *
 * @param <T> Тип об'єкта, із яким взаємодіє DAO.
 */
public interface BaseDao<T> {

    /**
     * Отримує всі записи з відповідної таблиці у базі даних.
     *
     * @return Список об'єктів типу {@code T}, які збережені у базі даних.
     */
    List<T> getAll();

    /**
     * Додає новий запис до бази даних або замінює існуючий, якщо виникає конфлікт.
     *
     * @param value Об'єкт типу {@code T}, який потрібно зберегти.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T value);

    /**
     * Додає список записів до бази даних.
     *
     * @param value Список об'єктів типу {@code T}, які потрібно зберегти.
     */
    @Insert
    void insert(List<T> value);

    /**
     * Видаляє запис із бази даних.
     *
     * @param value Об'єкт типу {@code T}, який потрібно видалити.
     */
    @Delete
    void delete(T value);

    /**
     * Видаляє список записів із бази даних.
     *
     * @param value Список об'єктів типу {@code T}, які потрібно видалити.
     */
    @Delete
    void delete(List<T> value);

}