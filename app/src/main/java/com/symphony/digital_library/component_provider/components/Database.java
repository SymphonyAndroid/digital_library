package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.database.dao.UserBookCrossRefDao;
import com.symphony.digital_library.data.database.dao.UserDao;

/**
 * Інтерфейс для доступу до DAO (Data Access Objects), які забезпечують взаємодію
 * з різними таблицями бази даних програми.
 */
public interface Database {

    /**
     * Метод для отримання DAO, який використовується для операцій з таблицею книг.
     *
     * @return {@link BookDao} — DAO для роботи з сутностями книг у базі даних.
     */
    BookDao getBookDao();

    /**
     * Метод для отримання DAO, який використовується для операцій з таблицею користувачів.
     *
     * @return {@link UserDao} — DAO для роботи з сутностями користувачів у базі даних.
     */
    UserDao getUserDao();

    /**
     * Метод для отримання DAO, який використовується для управління зв'язками між
     * користувачами та книгами.
     *
     * @return {@link UserBookCrossRefDao} — DAO для роботи із сутностями зв'язку між користувачами і книгами.
     */
    UserBookCrossRefDao getUserBookDao();
}