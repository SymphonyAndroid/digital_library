package com.symphony.digital_library.mvp.search_book;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BaseMvp;

import java.util.List;

/**
 * Інтерфейс {@code SearchBookMvp} описує контракт для функціональності екрана пошуку книг
 * у структурі MVP (Model-View-Presenter).
 * <p>
 * Містить два вкладені інтерфейси: {@link View} та {@link Presenter},
 * які визначають відповідно поведінку для View та Presenter.
 * </p>
 */
public interface SearchBookMvp {

    /**
     * Інтерфейс {@code View} визначає методи, які мають бути реалізовані UI-компонентами
     * для відображення результатів пошуку, стану прогресу та взаємодії з користувачем.
     */
    interface View extends BaseMvp.BaseView {

        /**
         * Показує список результатів пошуку книг.
         *
         * @param result Список {@link Book}, який є результатом пошуку.
         */
        void showResult(List<Book> result);

        /**
         * Відображає телефон користувача, знайденого за назвою книги.
         *
         * @param phone Номер телефону користувача.
         */
        void showUserPhone(String phone);

        /**
         * Сповіщає про успішне взяття книги користувачем.
         *
         * @param userName Ім'я користувача, який взяв книгу.
         * @param bookName Назва книги, яку було взято.
         */
        void onTakeSuccess(String userName, String bookName);

        /**
         * Показує або приховує індикатор прогресу завантаження.
         *
         * @param isVisible {@code true}, якщо індикатор має бути видимим; {@code false} в іншому випадку.
         */
        void showProgressBar(boolean isVisible);
    }

    /**
     * Інтерфейс {@code Presenter} визначає логіку, пов'язану з пошуком книг
     * та взаємодією з користувачем на екрані пошуку.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {

        /**
         * Викликається при зміні пошукового запиту.
         * <p>
         * Цей метод ініціює новий пошук книг відповідно до оновленого запиту.
         * </p>
         *
         * @param query Новий пошуковий запит.
         */
        void onQueryChanged(String query);

        /**
         * Викликається при зміні імені користувача.
         * <p>
         * Метод використовується для фільтрації/обробки даних, що пов'язані з іменем користувача.
         * </p>
         *
         * @param name Нове значення імені користувача.
         */
        void onNameChanged(String name);

        /**
         * Викликається при зміні номера телефону користувача.
         * <p>
         * Метод може бути використаний для зв'язку телефона з конкретною книгою.
         * </p>
         *
         * @param phone Новий номер телефону користувача.
         * @param book  Книга, пов'язана з номером телефону.
         */
        void onPhoneChanged(String phone, Book book);
    }
}