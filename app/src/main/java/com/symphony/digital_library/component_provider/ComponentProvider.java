package com.symphony.digital_library.component_provider;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.components.*;
import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Function;

public interface ComponentProvider {

    @NonNull
    Preferences getPreferences();
    @NonNull
    AppSchedulers getSchedulers();
    @NonNull
    UseCases getUseCases();
    @NonNull
    <D extends BaseDao<R>, R> CacheWithAction<D, R> getCacheWithAction(Function<Database, D> function);


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
