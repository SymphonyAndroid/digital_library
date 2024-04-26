package com.symphony.digital_library.mvp.take_book;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.mvp.base.BasePresenterImpl;

import io.reactivex.disposables.Disposable;

public class TakeBookPresenter extends BasePresenterImpl<TakeBookMvp.View> implements TakeBookMvp.Presenter {

    private Book book;
    @Override
    public void onSubmitClick(String name, String phone) {
        if (name.isEmpty() || phone.isEmpty()) {
            withView(view -> {
                if (name.isEmpty()) view.showNameError();
                if (phone.isEmpty()) view.showPhoneError();
            });
        } else subscriptions(() -> userTakeBook(new User(name, phone)));
    }

    @Override
    public void onBookChange(Book book) {
        this.book = book;
    }

    private Disposable userTakeBook(User user) {
        return getUseCases()
                .bookTaken()
                .invoke(new UserBookCrossRef(book.getId(), user.getName()))
                .observeOn(getSchedulers().ui())
                .subscribe(this::onSuccess, this::onError);
    }
    private void onSuccess() {
        withView(TakeBookMvp.View::onFinishTakeBook);
    }

    private void onError(Throwable throwable) {

    }
}
