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
import com.symphony.digital_library.data.use_case.impl.GetBooksFromNetworkUseCase;
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
/**
 * Клас {@code UseCasesImpl} реалізує інтерфейс {@link UseCases} та надає конкретні реалізації
 * різних сценаріїв використання для цифрової бібліотеки.
 * <p>
 * Цей клас служить фабрикою для створення різних сценаріїв використання, таких як пошук книг,
 * управління користувачами та операції з книгами. Він використовує контекст Android для
 * доступу до системних ресурсів.
 * </p>
 */
public class UseCasesImpl implements UseCases {

    /**
     * Контекст Android додатку.
     */
    private final Context context;

    /**
     * Створює новий екземпляр {@code UseCasesImpl}.
     *
     * @param context Контекст Android додатку, необхідний для деяких операцій.
     */
    public UseCasesImpl(Context context) {
        this.context = context;
    }

    /**
     * Створює сценарій використання для отримання всіх книг.
     *
     * @return Сценарій використання, що повертає список всіх книг у бібліотеці.
     */
    @Override
    public UseCase<Single<List<Book>>> getAllBooks() {
        return new GetAllBooksUseCase();
    }

    /**
     * Створює сценарій використання для отримання всіх книг.
     *
     * @return Сценарій використання, що повертає список всіх книг у бібліотеці з мережі.
     */
    @Override
    public UseCase<Single<List<Book>>> getAllBooksFromNetwork() {
        return new GetBooksFromNetworkUseCase();
    }

    /**
     * Створює сценарій використання для заповнення бази даних початковими даними.
     *
     * @return Сценарій використання для ініціалізації бази даних.
     */
    @Override
    public UseCase<Completable> fillingDatabase() {
        return new FillingBooksInDatabaseUseCase(context);
    }

    /**
     * Створює сценарій використання для пошуку книг за критерієм.
     *
     * @return Сценарій використання для пошуку книг.
     */
    @Override
    public UseCaseParams<String, Single<List<Book>>> findBooks() {
        return new FindBooksUseCase();
    }

    /**
     * Створює сценарій використання для пошуку користувача за ім'ям.
     *
     * @return Сценарій використання для пошуку одного користувача.
     */
    @Override
    public UseCaseParams<String, Single<User>> findUserByName() {
        return new FindUserUseCase();
    }

    /**
     * Створює сценарій використання для пошуку користувачів за критерієм.
     *
     * @return Сценарій використання для пошуку списку користувачів.
     */
    @Override
    public UseCaseParams<String, Single<List<User>>> findUsersByName() {
        return new FindUsersUseCase();
    }

    /**
     * Створює сценарій використання для реєстрації взяття книги користувачем.
     *
     * @return Сценарій використання для операції взяття книги.
     */
    @Override
    public UseCaseParams<Pair<User, Book>, Completable> bookTaken() {
        return new UserTakeBookUseCase();
    }

    /**
     * Створює сценарій використання для отримання списку книг конкретного користувача.
     *
     * @return Сценарій використання для отримання книг користувача.
     */
    @Override
    public UseCaseParams<User, Single<List<Book>>> booksByUser() {
        return new BooksByUserUseCase();
    }

    /**
     * Створює сценарій використання для повернення книги до бібліотеки.
     *
     * @return Сценарій використання для операції повернення книги.
     */
    @Override
    public UseCaseParams<UserBookCrossRef, Completable> returnBook() {
        return new ReturnBookUseCase();
    }
}