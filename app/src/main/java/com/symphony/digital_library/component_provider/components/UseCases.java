package com.symphony.digital_library.component_provider.components;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kotlin.Pair;

public interface UseCases {

    UseCase<Single<List<Book>>> getAllBooks();
    UseCase<Single<List<User>>> getAllUsers();
    UseCase<Completable> fillingDatabase();
    UseCaseParams<String, Single<List<Book>>> findBooks();
    UseCaseParams<String, Single<List<User>>> findUsersByName();
    UseCaseParams<String, Single<User>> findUserByName();
    UseCaseParams<Pair<User, Book>, Completable> bookTaken();
    UseCaseParams<User, Single<List<Book>>> booksByUser();
    UseCaseParams<UserBookCrossRef, Completable> returnBook();
}
