package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.database.dao.BookDao;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Single;

public class FindBooksUseCase implements UseCaseParams<String, Single<List<Book>>> {

    @Override
    public Single<List<Book>> invoke(String query) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getBookDao)
                .single(bookDao -> bookDao.findByQuery("%" + query + "%"));
    }

}
