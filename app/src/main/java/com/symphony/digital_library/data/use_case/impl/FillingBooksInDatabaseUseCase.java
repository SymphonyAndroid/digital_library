package com.symphony.digital_library.data.use_case.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.symphony.digital_library.R;
import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.use_case.UseCase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class FillingBooksInDatabaseUseCase implements UseCase<Completable> {

    private final Context context;
    public FillingBooksInDatabaseUseCase(Context context) {
        this.context = context;
    }

    @Override
    public Completable invoke() {
        return Single.fromCallable(() -> {
            InputStream is = context.getResources().openRawResource(R.raw.base_books);
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            return new Gson().fromJson(reader, new TypeToken<List<Book>>() { });
        })
                .subscribeOn(getProvider().getSchedulers().io())
                .flatMapCompletable(it -> getProvider().getCache(Database::getBookDao).insertAll(it));
    }

    private ComponentProvider getProvider() {
        return ComponentProvider.Companion.getInstance();
    }

}
