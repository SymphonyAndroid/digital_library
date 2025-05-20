package com.symphony.digital_library.mvp.splash;

import com.symphony.digital_library.mvp.base.BaseMvp;

/**
 * Інтерфейс {@code SplashMvp} описує контракт для Splash-екрану
 * у структурі MVP (Model-View-Presenter).
 * <p>
 * Метою є визначення базової логіки, що потрібна для відображення початкового екрана
 * та переходу до головного екрана.
 * </p>
 */
public interface SplashMvp {

    /**
     * Інтерфейс {@code View} визначає методи, які мають бути реалізовані в UI-компоненті Splash-екрану.
     */
    interface View extends BaseMvp.BaseView {

        /**
         * Відкриває головний екран після завершення виконання підготовчих операцій Splash-екрану.
         */
        void openMainScreen();

    }

    /**
     * Інтерфейс {@code Presenter} визначає логіку, яка має бути реалізована для Splash-екрану.
     * <p>
     * Даний інтерфейс не містить додаткових методів на даний момент, окрім визначених у {@link BaseMvp.BasePresenter}.
     * </p>
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {

    }

}