package com.symphony.digital_library.mvp.search_user;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Клас {@code SearchUserPresenter} реалізує логіку для Presenter екрану пошуку користувачів
 * у структурі MVP (Model-View-Presenter).
 * <p>
 * Клас забезпечує взаємодію між View та моделлю, зокрема виконує пошук користувачів,
 * обробляє дії користувача на екрані та повернення книг.
 * </p>
 */
public class SearchUserPresenter extends BasePresenterImpl<SearchUserMvp.View> implements SearchUserMvp.Presenter {

    private User user;

    /**
     * Викликається після з'єднання View із Presenter.
     * <p>
     * Ініціює перший пошук користувачів з порожнім параметром (всі користувачі).
     * </p>
     *
     * @param view З'єднане View.
     */
    @Override
    protected void onViewAttached(@NonNull SearchUserMvp.View view) {
        super.onViewAttached(view);
        subscriptions(() -> findUserByName(""));
    }

    /**
     * Обробляє зміну пошукового запиту для пошуку користувачів.
     * <p>
     * При зміні введеного тексту пошуку виконує оновлення списку користувачів.
     * </p>
     *
     * @param query Новий запит для пошуку користувачів.
     */
    @Override
    public void onQueryChanged(String query) {
        subscriptions(() -> findUserByName(query));
    }

    /**
     * Виконує пошук користувачів за їхнім іменем.
     *
     * @param name Пошуковий запит з іменем користувача.
     * @return Об'єкт {@link Disposable} для керування RxJava-підпискою.
     */
    private Disposable findUserByName(String name) {
        return getUseCases()
                .findUsersByName()
                .invoke(name)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onUserSuccess);
    }

    /**
     * Викликається після успішного пошуку користувачів.
     * <p>
     * Передає список знайдених користувачів у View для відображення.
     * </p>
     *
     * @param users Список знайдених користувачів {@link List<User>}.
     */
    private void onUserSuccess(List<User> users) {
        withView(view -> view.showUsers(users));
    }

    /**
     * Обробляє дію натискання на користувача.
     * <p>
     * Зберігає вибраного користувача та виконує пошук книг,
     * які користувач взяв.
     * </p>
     *
     * @param user Вибраний об'єкт {@link User}.
     */
    @Override
    public void onUserClick(User user) {
        this.user = user;
        subscriptions(() -> findTakenBooks(user));
    }

    /**
     * Виконує пошук книг, які були взяті вибраним користувачем.
     *
     * @param user Вибраний користувач.
     * @return Об'єкт {@link Disposable} для керування RxJava-підпискою.
     */
    private Disposable findTakenBooks(User user) {
        return getUseCases()
                .booksByUser()
                .invoke(user)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onSearchSuccess, this::onError);
    }

    /**
     * Викликається після успішного пошуку книг користувача.
     * <p>
     * Якщо списку книг немає, викликає {@code onEmptyBooks}, інакше оновлює список книг у View.
     * </p>
     *
     * @param books Список знайдених книг {@link List<Book>}.
     */
    private void onSearchSuccess(List<Book> books) {
        withView(view -> {
            if (books.isEmpty()) view.onEmptyBooks(user.getName());
            else view.updateBooks(books);
        });
    }

    /**
     * Обробляє дію повернення книги.
     *
     * @param book Об'єкт книги {@link Book}, яку потрібно повернути.
     */
    @Override
    public void onReturnBook(Book book) {
        subscriptions(() -> returnBook(book));
    }

    /**
     * Виконує повернення книги конкретного користувача.
     *
     * @param book Книга, яка повертається.
     * @return Об'єкт {@link Disposable} для керування RxJava-підпискою.
     */
    private Disposable returnBook(Book book) {
        return getUseCases()
                .returnBook()
                .invoke(new UserBookCrossRef(book.getId(), user.getName()))
                .observeOn(getSchedulers().ui())
                .subscribe(() -> onBookReturnedSuccess(user, book), this::onError);
    }

    /**
     * Викликається після успішного повернення книги.
     * <p>
     * Інформує View про те, що користувач успішно повернув книгу.
     * </p>
     *
     * @param user Користувач, який повернув книгу.
     * @param book Книга, яка була повернута.
     */
    private void onBookReturnedSuccess(User user, Book book) {
        withView(view -> view.onBookReturned(user.getName(), book.getTitle()));
    }
}