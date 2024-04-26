package com.symphony.digital_library.component_provider.impl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.Cache;
import com.symphony.digital_library.component_provider.components.Preferences;
import com.symphony.digital_library.component_provider.components.UseCases;
import com.symphony.digital_library.component_provider.impl.database.AppDatabase;
import com.symphony.digital_library.component_provider.impl.database.CacheImpl;
import com.symphony.digital_library.component_provider.impl.scheduler.SchedulersImpl;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.component_provider.impl.use_cases.UseCasesImpl;
import com.symphony.digital_library.data.database.dao.BaseDao;
import com.symphony.digital_library.data.preferences.PreferencesImpl;

import java.util.function.Function;


public class ComponentProviderImpl implements ComponentProvider {

    private final Context context;
    private Preferences preferences;
    private AppSchedulers schedulers;
    private Database database;
    private UseCases useCases;

    public ComponentProviderImpl(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Preferences getPreferences() {
        if (preferences == null) preferences = new PreferencesImpl(context);
        return preferences;
    }

    @NonNull
    @Override
    public AppSchedulers getSchedulers() {
        if (schedulers == null) schedulers = new SchedulersImpl();
        return schedulers;
    }

    @NonNull
    @Override
    public UseCases getUseCases() {
        if (useCases == null) useCases = new UseCasesImpl(context);
        return useCases;
    }

    @NonNull
    @Override
    public <D extends BaseDao<T>, T> Cache<T> getCache(Function<Database, D> function) {
        return new CacheImpl<>(function.apply(getDatabase()));
    }

    @NonNull
    private Database getDatabase() {
        if (database == null) database = Room
                .databaseBuilder(context, AppDatabase.class, "library")
                .fallbackToDestructiveMigration()
                .build();
        return database;
    }

}
