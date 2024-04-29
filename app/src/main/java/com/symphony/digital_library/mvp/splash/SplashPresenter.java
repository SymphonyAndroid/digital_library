package com.symphony.digital_library.mvp.splash;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SplashPresenter extends BasePresenterImpl<SplashMvp.View> implements SplashMvp.Presenter {

    @Override
    protected void onViewAttached(@NonNull SplashMvp.View view) {
        super.onViewAttached(view);
        subscriptions(this::getAllBooks);
    }

    private Disposable getAllBooks() {
        return getUseCases()
                .getAllBooks()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
    }

    private void onResult(List<Book> books) {
        if (!books.isEmpty()) openMainScreen();
        else subscriptions(this::fillingDatabase);
    }

    private Disposable fillingDatabase() {
        return getUseCases()
                .fillingDatabase()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::openMainScreen, this::onError);
    }

    private void openMainScreen() {
        withView(SplashMvp.View::openMainScreen);
    }

}
