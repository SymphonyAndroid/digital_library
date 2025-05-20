package com.symphony.digital_library.mvp.search_book;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;
import kotlin.Pair;

/**
 * Клас {@code SearchBookPresenter} реалізує логіку для Presenter екрану пошуку книг
 * у рамках архітектури MVP (Model-View-Presenter).
 * <p>
 * Цей Presenter відповідає за взаємодію між View та моделлю, обробку даних пошуку
 * книг, користувачів та дій, пов'язаних із видачею книг.
 * </p>
 */
public class SearchBookPresenter extends BasePresenterImpl<SearchBookMvp.View> implements SearchBookMvp.Presenter {

    private String name = "";
    private String query = "";

    /**
     * Викликається при успішному підключенні View до Presenter.
     * <p>
     * Ініціює виконання першого запиту для пошуку книг.
     * </p>
     *
     * @param view Прикріплене View.
     */
    @Override
    protected void onViewAttached(@NonNull SearchBookMvp.View view) {
        super.onViewAttached(view);
        subscriptions(this::findByQuery);
    }

    /**
     * Обробляє зміну пошукового запиту.
     * <p>
     * Оновлює значення пошукового параметра та виконує запит на пошук книг.
     * </p>
     *
     * @param query Новий пошуковий запит.
     */
    @Override
    public void onQueryChanged(String query) {
        this.query = query;
        subscriptions(this::findByQuery);
    }

    /**
     * Обробляє зміну імені користувача.
     * <p>
     * Виконує пошук інформації про користувача за його іменем.
     * </p>
     *
     * @param name Нове значення імені користувача.
     */
    @Override
    public void onNameChanged(String name) {
        subscriptions(() -> findByUserName(name));
    }

    /**
     * Виконує пошук користувача за ім'ям.
     *
     * @param name Ім'я користувача, за яким здійснюється пошук.
     * @return Об'єкт {@link Disposable} для керування підпискою RxJava.
     */
    public Disposable findByUserName(String name) {
        withView(view -> view.showProgressBar(true));
        this.name = name;
        return getUseCases()
                .findUserByName()
                .invoke(name)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onUserSuccess, this::onUserError);
    }

    /**
     * Викликається, коли інформація про користувача успішно знайдена.
     * <p>
     * Передає телефон користувача до View та приховує індикатор прогресу.
     * </p>
     *
     * @param user Об'єкт {@link User}, що містить інформацію про знайденого користувача.
     */
    private void onUserSuccess(User user) {
        withView(view -> {
            view.showUserPhone(user.getPhone());
            view.showProgressBar(false);
        });
    }

    /**
     * Викликається, якщо пошук користувача зазнав невдачі.
     * <p>
     * Приховує індикатор прогресу та очищує інформацію про телефон користувача.
     * </p>
     *
     * @param throwable Об'єкт помилки.
     */
    private void onUserError(Throwable throwable) {
        withView(view -> {
            view.showUserPhone("");
            view.showProgressBar(false);
        });
    }

    /**
     * Обробляє зміну номера телефону користувача.
     * <p>
     * Ініціює дію "взяти книгу" для переданого користувача та книги.
     * </p>
     *
     * @param phone Номер телефону користувача.
     * @param book  Об'єкт {@link Book}, який користувач бере.
     */
    @Override
    public void onPhoneChanged(String phone, Book book) {
        subscriptions(() -> userTakeBook(new User(name, phone), book));
    }

    /**
     * Виконує дію "взяти книгу" для заданого користувача та книги.
     *
     * @param user Об'єкт {@link User}, який бере книгу.
     * @param book Об'єкт {@link Book}, що видається користувачу.
     * @return Об'єкт {@link Disposable} для керування підпискою RxJava.
     */
    private Disposable userTakeBook(User user, Book book) {
        return getUseCases()
                .bookTaken()
                .invoke(new Pair<>(user, book))
                .observeOn(getSchedulers().ui())
                .subscribe(() -> onTakeSuccess(user, book), this::onError);
    }

    /**
     * Викликається після успішного виконання дії "взяти книгу".
     * <p>
     * Оновлює View з інформацією про успішну операцію та ініціює новий пошук книг.
     * </p>
     *
     * @param user Об'єкт {@link User}, який взяв книгу.
     * @param book Об'єкт {@link Book}, що був виданий.
     */
    private void onTakeSuccess(User user, Book book) {
        withView(view -> view.onTakeSuccess(user.getName(), book.getTitle()));
        subscriptions(this::findByQuery);
    }

    /**
     * Виконує пошук книг за наявним пошуковим запитом.
     *
     * @return Об'єкт {@link Disposable} для керування підпискою RxJava.
     */
    private Disposable findByQuery() {
        return getUseCases()
                .findBooks()
                .invoke(query)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
    }

    /**
     * Обробляє отримані результати пошуку книг.
     *
     * @param result Список знайдених книг {@link List<Book>}.
     */
    private void onResult(List<Book> result) {
        withView(view -> view.showResult(result));
    }
}