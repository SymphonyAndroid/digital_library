package com.symphony.digital_library.mvp.base;

import androidx.annotation.NonNull;

import com.symphony.digital_library.util.function.NotNullConsumer;

/**
 * Інтерфейс {@code BaseMvp} визначає базову структуру для патерна MVP (Model-View-Presenter).
 * <p>
 * Містить вкладені інтерфейси {@link BaseView} та {@link BasePresenter}, які задають базові вимоги
 * для View та Presenter відповідно.
 * </p>
 */
public interface BaseMvp {

    /**
     * Інтерфейс {@code BaseView} визначає базову поведінку для View у структурі MVP.
     */
    interface BaseView {
        /**
         * Відображає повідомлення про помилку у вигляді тосту.
         *
         * @param message Текст повідомлення про помилку.
         */
        void showErrorToast(String message);
    }

    /**
     * Інтерфейс {@code BasePresenter} визначає базову поведінку для Presenter у структурі MVP.
     *
     * @param <V> Тип View, який успадковується від {@link BaseView}.
     */
    interface BasePresenter<V extends BaseView> {

        /**
         * Прикріплює View до Presenter.
         *
         * @param view Екземпляр View, який потрібно зв'язати з Presenter.
         */
        void attachView(@NonNull V view);

        /**
         * Від'єднує View від Presenter.
         */
        void detachView();

        /**
         * Виконує дію з прикріпленим View.
         * <p>
         * Якщо View прикріплено, передає його в обробник дій {@link NotNullConsumer}.
         * </p>
         *
         * @param action Дія, яку потрібно виконати з View.
         */
        void withView(@NonNull NotNullConsumer<V> action);
    }
}