package com.symphony.digital_library.data.use_case.impl;


import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Single;

public class FindUsersUseCase implements UseCaseParams<String, Single<List<User>>> {

    @Override
    public Single<List<User>> invoke(String item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserDao)
                .single(userDao -> userDao.findUsersByName("%" + item + "%"));
    }

}
