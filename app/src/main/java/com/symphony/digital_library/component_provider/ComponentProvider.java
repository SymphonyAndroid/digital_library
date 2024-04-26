package com.symphony.digital_library.component_provider;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.Cache;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.component_provider.components.Preferences;
import com.symphony.digital_library.component_provider.components.UseCases;
import com.symphony.digital_library.data.database.dao.BaseDao;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;

import java.util.function.Function;

public interface ComponentProvider {

    @NonNull
    Preferences getPreferences();
    @NonNull
    AppSchedulers getSchedulers();
    @NonNull
    UseCases getUseCases();
    @NonNull
    <D extends BaseDao<T>, T> Cache<T> getCache(Function<Database, D> function);


    class Companion {
        private static ComponentProvider instance;

        @NonNull
        public static ComponentProvider getInstance() {
            return instance;
        }

        public static void inject(@NonNull ComponentProvider instance) {
            Companion.instance = instance;
        }
    }
}
