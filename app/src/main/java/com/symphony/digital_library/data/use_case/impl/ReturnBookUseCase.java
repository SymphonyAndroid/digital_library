package com.symphony.digital_library.data.use_case.impl;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.use_case.UseCaseParams;

import io.reactivex.Completable;

/**
 * Клас {@code ReturnBookUseCase} реалізує сценарій повернення книги користувачем.
 * <p>
 * Цей сценарій видаляє запис про зв'язок між користувачем і книгою ({@link UserBookCrossRef}),
 * що означає повернення книги до бібліотеки.
 * Операція виконується у вигляді асинхронного запиту, результатом якого є {@link Completable}.
 * </p>
 */
public class ReturnBookUseCase implements UseCaseParams<UserBookCrossRef, Completable> {

    /**
     * Видаляє запис про зв'язок між користувачем і книгою в базі даних.
     *
     * @param item Об'єкт {@link UserBookCrossRef}, що містить інформацію про зв'язок користувача й книги,
     *             який необхідно видалити (повернення книги).
     * @return Асинхронний об'єкт {@code Completable}, що сигналізує про завершення операції.
     *         Якщо виникає помилка, Completable передає відповідний сигнал помилки.
     */
    @Override
    public Completable invoke(UserBookCrossRef item) {
        return ComponentProvider.Companion.getInstance()
                .getCacheWithAction(Database::getUserBookDao)
                .completable(dao -> dao.delete(item));
    }
}