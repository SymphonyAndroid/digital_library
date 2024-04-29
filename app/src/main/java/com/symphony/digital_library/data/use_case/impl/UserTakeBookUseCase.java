package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import io.reactivex.Completable;
import kotlin.Pair;

public class UserTakeBookUseCase implements UseCaseParams<Pair<User, Book>, Completable> {

    @Override
    public Completable invoke(Pair<User, Book> item) {
        return addUser(item.getFirst()).concatWith(insertBook(item.getSecond().getId(), item.getFirst().getName()));
    }

    private Completable addUser(User user) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .completable(userDao -> userDao.insert(user));
    }

    private Completable insertBook(String id, String name) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserBookDao)
                .completable(dao -> dao.insert(new UserBookCrossRef(id, name)));
    }

}
