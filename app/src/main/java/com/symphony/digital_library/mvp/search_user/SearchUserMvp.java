package com.symphony.digital_library.mvp.search_user;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.mvp.base.BaseMvp;

import java.util.List;

public interface SearchUserMvp {

    interface View extends BaseMvp.BaseView {

        void showUsers(List<User> users);

        void updateBooks(List<Book> books);

        void onEmptyBooks();

        void onBookReturned(String name, String title);
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onQueryChanged(String query);

        void onUserClick(User user);

        void onReturnBook(Book book);
    }

}
