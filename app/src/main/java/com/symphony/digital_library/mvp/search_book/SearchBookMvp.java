package com.symphony.digital_library.mvp.search_book;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.mvp.base.BaseMvp;

import java.util.List;

public interface SearchBookMvp {

    interface View extends BaseMvp.BaseView {

        void showResult(List<Book> result);

        void showUserPhone(String phone);

        void onTakeSuccess(String userName, String bookName);

        void showProgressBar(boolean isVisible);
    }
    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onQueryChanged(String query);

        void onNameChanged(String name);

        void onPhoneChanged(String phone, Book book);
    }

}
