package com.symphony.digital_library.component_provider.impl.database;

import androidx.room.RoomDatabase;

import com.symphony.digital_library.component_provider.components.Database;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserBookCrossRef;
import com.symphony.digital_library.data.entity.UserTakenBook;

@androidx.room.Database(
        entities = {
                Book.class,
                User.class,
                UserBookCrossRef.class,
                UserTakenBook.class,
        },
        version = 1
)

public abstract class AppDatabase extends RoomDatabase implements Database  {
}
