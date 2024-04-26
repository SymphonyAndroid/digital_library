package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;

import java.util.List;

import io.reactivex.Single;

public class FindBooksUseCase implements UseCase<Single<List<Book>>> {

    private final String query;
    public FindBooksUseCase(String query) {
        this.query = "%" + query + "%";
    }

    @Override
    public Single<List<Book>> invoke() {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getBookDao)
                .single(bookDao -> bookDao.findByQuery(query));
    }

}
