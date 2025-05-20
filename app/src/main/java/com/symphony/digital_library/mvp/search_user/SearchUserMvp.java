package com.symphony.digital_library.mvp.search_user;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.mvp.base.BaseMvp;

import java.util.List;

/**
 * Інтерфейс {@code SearchUserMvp} описує контракт для функціональності екрана пошуку користувачів
 * у структурі MVP (Model-View-Presenter).
 * <p>
 * Містить два вкладені інтерфейси: {@link View} та {@link Presenter},
 * які визначають відповідно поведінку для View та Presenter.
 * </p>
 */
public interface SearchUserMvp {

    /**
     * Інтерфейс {@code View} визначає методи, які мають бути реалізовані UI-компонентами
     * для відображення списків користувачів і книг, керування станами,
     * а також обробки подій на екрані взаємодії з користувачами.
     */
    interface View extends BaseMvp.BaseView {

        /**
         * Показує список знайдених користувачів.
         *
         * @param users Список об'єктів {@link User}, які відповідають пошуковому запиту.
         */
        void showUsers(List<User> users);

        /**
         * Оновлює список книг, пов'язаних із користувачем.
         *
         * @param books Список об'єктів {@link Book}, що пов'язані з вибраним користувачем.
         */
        void updateBooks(List<Book> books);

        /**
         * Сповіщає про відсутність книг у вибраного користувача.
         *
         * @param name Ім'я користувача, у якого немає книг.
         */
        void onEmptyBooks(String name);

        /**
         * Сповіщає про успішне повернення книги користувачем.
         *
         * @param name  Ім'я користувача, який повернув книгу.
         * @param title Назва книги, яку повернули.
         */
        void onBookReturned(String name, String title);
    }

    /**
     * Інтерфейс {@code Presenter} визначає логіку, пов'язану з пошуком користувачів,
     * взаємодією з обраними користувачами та процесом повернення книг.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {

        /**
         * Обробляє зміну пошукового запиту.
         * <p>
         * Ініціює процес пошуку користувачів відповідно до нового запиту.
         * </p>
         *
         * @param query Новий пошуковий запит.
         */
        void onQueryChanged(String query);

        /**
         * Обробляє вибір користувача зі списку.
         * <p>
         * Викликає запит для отримання списку книг, пов'язаних із вибраним користувачем.
         * </p>
         *
         * @param user Обраний об'єкт {@link User}.
         */
        void onUserClick(User user);

        /**
         * Обробляє дію користувача щодо повернення книги.
         * <p>
         * Оновлює дані системи про повернуту книгу та викликає оновлення у відповідному View.
         * </p>
         *
         * @param book Об'єкт {@link Book}, що повертається.
         */
        void onReturnBook(Book book);
    }

}