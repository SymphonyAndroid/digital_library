package com.symphony.digital_library.component_provider.impl.database;

import androidx.room.RoomDatabase;

import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;

/**
 * Реалізація бази даних додатку з використанням бібліотеки Room.
 * <p>
 * Клас слугує абстрактною реалізацією бази даних і використовується для
 * доступу до DAO (Data Access Objects).
 * </p>
 * <p>
 * Ця база даних містить такі сутності:
 * <ul>
 *     <li>{@link Book} — сутність книги.</li>
 *     <li>{@link User} — сутність користувача.</li>
 *     <li>{@link UserBookCrossRef} — сутність для визначення зв'язку між користувачами та книгами.</li>
 * </ul>
 * </p>
 * @see RoomDatabase Базовий клас бази даних Room.
 */
@androidx.room.Database(
        entities = {
                Book.class,
                User.class,
                UserBookCrossRef.class,
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase implements Database {
}