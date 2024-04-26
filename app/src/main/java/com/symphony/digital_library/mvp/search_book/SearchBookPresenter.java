package com.symphony.digital_library.mvp.search_book;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SearchBookPresenter extends BasePresenterImpl<SearchBookMvp.View> implements SearchBookMvp.Presenter {

    @Override
    protected void onViewAttached(@NonNull SearchBookMvp.View view) {
        super.onViewAttached(view);

    }

    @Override
    public void onQueryChanged(String query) {
        subscriptions(() -> findByQuery(query));
    }

    private Disposable findByQuery(String query) {
        return getUseCases()
                .findBooks(query)
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onResult, this::onError);
    }

    private void onResult(List<Book> result) {
        withView(view -> view.showResult(result));
    }

    private void onError(Throwable throwable) {

    }
}
