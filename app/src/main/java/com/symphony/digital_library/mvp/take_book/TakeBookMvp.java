package com.symphony.digital_library.mvp.take_book;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BaseMvp;

public interface TakeBookMvp {

    interface View extends BaseMvp.BaseView {

        void showNameError();
        void showPhoneError();
        void onFinishTakeBook();
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {
        void onSubmitClick(String name, String phone);

        void onBookChange(Book book);
    }
}
