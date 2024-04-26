package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UseCases {

    UseCase<Single<List<Book>>> getAllBooks();
    UseCase<Single<List<User>>> getAllUsers();
    UseCase<Completable> fillingDatabase();
    UseCase<Single<List<Book>>> findBooks(String query);
    UseCaseParams<UserBookCrossRef, Completable> bookTaken();
}
