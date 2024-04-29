package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class BooksByUserUseCase implements UseCaseParams<User, Single<List<Book>>> {

    @Override
    public Single<List<Book>> invoke(User item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(dao -> dao.booksByUser(item.getName()))
                .map(it -> it.getBooks() == null ? new ArrayList<>() : it.getBooks());
    }

}
