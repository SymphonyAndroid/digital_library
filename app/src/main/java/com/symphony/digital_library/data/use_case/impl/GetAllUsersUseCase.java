package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCase;

import java.util.List;

import io.reactivex.Single;

public class GetAllUsersUseCase implements UseCase<Single<List<User>>> {

    @Override
    public Single<List<User>> invoke() {
        return ComponentProvider.Companion.getInstance().getCache(Database::getUserDao).getAll();
    }

}
