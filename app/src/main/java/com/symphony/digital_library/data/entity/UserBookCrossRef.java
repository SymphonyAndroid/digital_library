package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"bookId", "userId"})
public class UserBookCrossRef {

    @NonNull private final String bookId;
    @NonNull private final String userId;

    public UserBookCrossRef(@NonNull String bookId, @NonNull String userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    @NonNull
    public String getBookId() {
        return bookId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }
}
