package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import io.reactivex.Completable;

public class ReturnBookUseCase implements UseCaseParams<UserBookCrossRef, Completable> {

    @Override
    public Completable invoke(UserBookCrossRef item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserBookDao)
                .completable(dao -> dao.delete(item));
    }
}
