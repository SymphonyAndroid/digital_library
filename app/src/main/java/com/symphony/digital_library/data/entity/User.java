package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Клас {@code User} представляє сутність користувача у системі.
 * <p>
 * Даний клас моделює таблицю в базі даних завдяки аннотації {@link Entity}.
 * Він містить основні атрибути користувача: ім'я та номер телефону.
 * Цей клас також реалізує інтерфейс {@link Serializable}, що дозволяє
 * серіалізувати об'єкт користувача для передачі між різними компонентами програми.
 * </p>
 */
@Entity
public class User implements Serializable {

    /**
     * Унікальне ім'я користувача, яке є первинним ключем у таблиці.
     */
    @PrimaryKey
    @NonNull
    private final String name;

    /**
     * Номер телефону користувача.
     */
    @NonNull
    private final String phone;

    /**
     * Конструктор для створення об'єкта {@code User} з заданим ім'ям і телефоном.
     *
     * @param name  Унікальне ім'я користувача.
     * @param phone Номер телефону користувача.
     * @throws NullPointerException Якщо передане ім'я або телефон є {@code null}.
     */
    public User(@NonNull String name, @NonNull String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Повертає ім'я користувача.
     *
     * @return Унікальне ім'я користувача.
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Повертає номер телефону користувача.
     *
     * @return Номер телефону користувача.
     */
    @NonNull
    public String getPhone() {
        return phone;
    }
}