package com.symphony.digital_library.mvp.main;

import com.symphony.digital_library.mvp.base.BaseMvp;

/**
 * Інтерфейс {@code MainMvp} визначає контракт для головного екрана в структурі MVP (Model-View-Presenter).
 * <p>
 * Містить два вкладені інтерфейси {@link View} та {@link Presenter},
 * які описують відповідно поведінку View і Presenter.
 * </p>
 */
public interface MainMvp {

    /**
     * Інтерфейс {@code View} визначає, які методи повинні бути реалізовані для View
     * головного екрана.
     */
    interface View extends BaseMvp.BaseView {

        /**
         * Відкриває екран пошуку книг.
         */
        void openSearchBookScreen();

        /**
         * Відкриває екран пошуку користувачів із обліку взятих книг.
         */
        void openSearchUserScreen();
    }

    /**
     * Інтерфейс {@code Presenter} визначає поведінку Presenter
     * для головного екрана.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {

        /**
         * Викликається, коли користувач натискає кнопку "Пошук книг".
         * <p>
         * Має ініціювати відкриття екрана пошуку книг через View.
         * </p>
         */
        void onSearchBookClick();

        /**
         * Викликається, коли користувач натискає кнопку "Пошук взятих книг користувачів".
         * <p>
         * Має ініціювати відкриття екрана пошуку користувачів через View.
         * </p>
         */
        void onSearchTakenBookClick();
    }

}