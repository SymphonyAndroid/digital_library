package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.ClientApi;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;

import java.util.List;

import io.reactivex.Single;

public class GetBooksFromNetworkUseCase implements UseCase<Single<List<Book>>> {

    @Override
    public Single<List<Book>> invoke() {
        return ClientApi.getApi()
                .getBooks()
                .flatMap(books -> ComponentProvider.Companion
                        .getInstance()
                        .getCacheWithAction(Database::getBookDao)
                        .single(bookDao -> {
                            bookDao.insert(books);
                            return books;
                        })
                )
                .subscribeOn(ComponentProvider.Companion.getInstance().getSchedulers().io());
    }
}
