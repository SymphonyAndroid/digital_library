package com.symphony.digital_library.component_provider.impl.use_cases;

import android.content.Context;

import com.symphony.digital_library.component_provider.components.UseCases;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCase;
import com.symphony.digital_library.data.use_case.UseCaseParams;
import com.symphony.digital_library.data.use_case.impl.BooksByUserUseCase;
import com.symphony.digital_library.data.use_case.impl.FindUserUseCase;
import com.symphony.digital_library.data.use_case.impl.FindUsersUseCase;
import com.symphony.digital_library.data.use_case.impl.ReturnBookUseCase;
import com.symphony.digital_library.data.use_case.impl.UserTakeBookUseCase;
import com.symphony.digital_library.data.use_case.impl.FillingBooksInDatabaseUseCase;
import com.symphony.digital_library.data.use_case.impl.FindBooksUseCase;
import com.symphony.digital_library.data.use_case.impl.GetAllBooksUseCase;
import com.symphony.digital_library.data.use_case.impl.GetAllUsersUseCase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kotlin.Pair;

public class UseCasesImpl implements UseCases {

    private final Context context;
    public UseCasesImpl(Context context) {
        this.context = context;
    }

    @Override
    public UseCase<Single<List<Book>>> getAllBooks() {
        return new GetAllBooksUseCase();
    }

    @Override
    public UseCase<Completable> fillingDatabase() {
        return new FillingBooksInDatabaseUseCase(context);
    }

    @Override
    public UseCaseParams<String, Single<List<Book>>> findBooks() {
        return new FindBooksUseCase();
    }

    @Override
    public UseCaseParams<String, Single<User>> findUserByName() {
        return new FindUserUseCase();
    }
    @Override
    public UseCaseParams<String, Single<List<User>>> findUsersByName() {
        return new FindUsersUseCase();
    }

    @Override
    public UseCaseParams<Pair<User, Book>, Completable> bookTaken() {
        return new UserTakeBookUseCase();
    }

    @Override
    public UseCaseParams<User, Single<List<Book>>> booksByUser() {
        return new BooksByUserUseCase();
    }

    @Override
    public UseCaseParams<UserBookCrossRef, Completable> returnBook() {
        return new ReturnBookUseCase();
    }
}
