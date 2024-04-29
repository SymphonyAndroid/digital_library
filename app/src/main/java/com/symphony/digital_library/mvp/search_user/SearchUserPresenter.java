package com.symphony.digital_library.mvp.search_user;


import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SearchUserPresenter extends BasePresenterImpl<SearchUserMvp.View> implements SearchUserMvp.Presenter {

    private User user;
    @Override
    public void onQueryChanged(String query) {
        subscriptions(() -> findUserByName(query));
    }

    private Disposable findUserByName(String name) {
        return getUseCases()
                .findUsersByName()
                .invoke(name)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onUserSuccess);
    }

    private void onUserSuccess(List<User> users) {
        withView(view -> view.showUsers(users));
    }

    @Override
    public void onUserClick(User user) {
        this.user = user;
        subscriptions(() -> findTakenBooks(user));
    }

    private Disposable findTakenBooks(User user) {
        return getUseCases()
                .booksByUser()
                .invoke(user)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onSearchSuccess, this::onError);
    }

    private void onSearchSuccess(List<Book> books) {
        withView(view -> {
            if (books.isEmpty()) view.onEmptyBooks();
            else view.updateBooks(books);
        });
    }

    @Override
    public void onReturnBook(Book book) {
        subscriptions(() -> returnBook(book));
    }

    private Disposable returnBook(Book book) {
        return getUseCases()
                .returnBook()
                .invoke(new UserBookCrossRef(book.getId(), user.getName()))
                .observeOn(getSchedulers().ui())
                .subscribe(() -> onBookReturnedSuccess(user, book), this::onUserError);
    }

    private void onBookReturnedSuccess(User user, Book book) {
        withView(view -> view.onBookReturned(user.getName(), book.getTitle()));
    }
}
