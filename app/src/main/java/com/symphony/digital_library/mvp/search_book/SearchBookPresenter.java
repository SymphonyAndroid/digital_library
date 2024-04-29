package com.symphony.digital_library.mvp.search_book;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;
import kotlin.Pair;

public class SearchBookPresenter extends BasePresenterImpl<SearchBookMvp.View> implements SearchBookMvp.Presenter {

    private String name = "";
    private String query = "";

    @Override
    protected void onViewAttached(@NonNull SearchBookMvp.View view) {
        super.onViewAttached(view);
        subscriptions(this::findByQuery);
    }

    @Override
    public void onQueryChanged(String query) {
        this.query = query;
        subscriptions(this::findByQuery);
    }

    @Override
    public void onNameChanged(String name) {
        subscriptions(() -> findByUserName(name));
    }

    public Disposable findByUserName(String name) {
        withView(view -> view.showProgressBar(true));
        this.name = name;
        return getUseCases()
                .findUserByName()
                .invoke(name)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onUserSuccess, this::onUserError);
    }

    private void onUserSuccess(User user) {
        withView(view -> {
            view.showUserPhone(user.getPhone());
            view.showProgressBar(false);
        });
    }

    private void onUserError(Throwable throwable) {
        withView(view -> {
            view.showUserPhone("");
            view.showProgressBar(false);
        });
    }
    @Override
    public void onPhoneChanged(String phone, Book book) {
        subscriptions(() -> userTakeBook(new User(name, phone), book));
    }

    private Disposable userTakeBook(User user, Book book) {
        return getUseCases()
                .bookTaken()
                .invoke(new Pair<>(user, book))
                .observeOn(getSchedulers().ui())
                .subscribe(() -> onTakeSuccess(user, book), this::onError);
    }

    private void onTakeSuccess(User user, Book book) {
        withView(view -> view.onTakeSuccess(user.getName(), book.getTitle()));
        subscriptions(this::findByQuery);
    }

    private Disposable findByQuery() {
        return getUseCases()
                .findBooks()
                .invoke(query)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
    }

    private void onResult(List<Book> result) {
        withView(view -> view.showResult(result));
    }
}
