package com.symphony.digital_library.mvp.splash;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Клас {@code SplashPresenter} реалізує логіку роботи Splash-екрану
 * в рамках структури MVP (Model-View-Presenter).
 * <p>
 * Основні завдання:
 * <ul>
 *     <li>Отримати перелік книг із системи.</li>
 *     <li>Перевірити стан локальної бази даних.</li>
 *     <li>Наповнити базу даних у разі необхідності.</li>
 *     <li>Перейти до головного екрана.</li>
 * </ul>
 * </p>
 */
public class SplashPresenter extends BasePresenterImpl<SplashMvp.View> implements SplashMvp.Presenter {

    /**
     * Викликається після прив'язки View до Presenter.
     * <p>
     * Ініціює процес завантаження всіх книг для перевірки стану даних у системі.
     * </p>
     *
     * @param view Прикріплене View для Splash-екрану.
     */
    @Override
    protected void onViewAttached(@NonNull SplashMvp.View view) {
        super.onViewAttached(view);
        subscriptions(this::getAllBooks);
    }

    /**
     * Завантажує всі книги з мережі.
     * <p>
     * Якщо книги успішно завантажено, перевіряється їхня кількість. Якщо список непорожній —
     * відбувається перехід на головний екран. Якщо список порожній — викликається метод {@code fillingDatabase},
     * який заповнює базу даних.
     * </p>
     *
     * @return Об'єкт {@link Disposable} для керування RxJava-підпискою.
     */
    private Disposable getAllBooks() {
        return getUseCases()
                .getAllBooks()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
    }

    /**
     * Обробляє результат завантаження книг.
     * <p>
     * Якщо список книг непорожній, відбувається перехід на головний екран.
     * Інакше запускається процес заповнення бази даних.
     * </p>
     *
     * @param books Список книг, отриманий із системи {@link List<Book>}.
     */
    private void onResult(List<Book> books) {
        if (!books.isEmpty()) openMainScreen();
        else subscriptions(this::fillingDatabase);
    }

    /**
     * Заповнює локальну базу даних початковими даними.
     * <p>
     * Після успішного завершення процесу переходить на головний екран.
     * </p>
     *
     * @return Об'єкт {@link Disposable} для керування RxJava-підпискою.
     */
    private Disposable fillingDatabase() {
        return getUseCases()
                .fillingDatabase()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::openMainScreen, this::onError);
    }

    /**
     * Відкриває головний екран програми.
     * <p>
     * Викликається як після успішного завантаження книг, так і після заповнення локальної бази даних.
     * </p>
     */
    private void openMainScreen() {
        withView(SplashMvp.View::openMainScreen);
    }

}