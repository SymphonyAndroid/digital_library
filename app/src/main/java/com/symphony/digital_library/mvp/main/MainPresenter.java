package com.symphony.digital_library.mvp.main;

import androidx.annotation.NonNull;

import com.symphony.digital_library.mvp.base.BasePresenterImpl;

/**
 * Клас {@code MainPresenter} реалізує логіку Presenter для головного екрана,
 * взаємодіючи з інтерфейсом View {@link MainMvp.View}.
 * <p>
 * Цей Presenter обробляє події, пов'язані з діями користувача,
 * та викликає відповідні методи View.
 * </p>
 */
public class MainPresenter extends BasePresenterImpl<MainMvp.View> implements MainMvp.Presenter {

    /**
     * Метод викликається після того, як View успішно прикріплено до Presenter.
     * <p>
     * Базова реалізація може бути використана для виконання додаткової ініціалізації
     * або підготовки View, якщо це потрібно.
     * </p>
     *
     * @param view Прикріплене View.
     */
    @Override
    protected void onViewAttached(@NonNull MainMvp.View view) {
        super.onViewAttached(view);
        // Додаткова логіка при підключенні View може бути реалізована тут.
    }

    /**
     * Обробляє подію натискання на кнопку "Пошук книг".
     * <p>
     * Викликається відповідний метод View {@link MainMvp.View#openSearchBookScreen()},
     * щоб перейти до екрана пошуку книг.
     * </p>
     */
    @Override
    public void onSearchBookClick() {
        withView(MainMvp.View::openSearchBookScreen);
    }

    /**
     * Обробляє подію натискання на кнопку "Пошук взятих книг".
     * <p>
     * Викликається відповідний метод View {@link MainMvp.View#openSearchUserScreen()},
     * щоб перейти до екрана пошуку користувачів із обліку взятих книг.
     * </p>
     */
    @Override
    public void onSearchTakenBookClick() {
        withView(MainMvp.View::openSearchUserScreen);
    }
}