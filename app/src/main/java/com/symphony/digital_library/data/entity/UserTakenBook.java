package com.symphony.digital_library.data.entity;


import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserTakenBook {

    @NonNull
    @Embedded
    private User user;

    @Relation(
            parentColumn = "name",
            entityColumn = "id",
            associateBy = @Junction(
                    value = UserBookCrossRef.class,
                    parentColumn = "userId",
                    entityColumn = "bookId"
            )
    )
    private List<Book> books;

    public UserTakenBook(@NonNull User user, List<Book> books) {
        this.user = user;
        this.books = books;
    }
}
