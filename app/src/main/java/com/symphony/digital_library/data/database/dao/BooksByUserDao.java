package com.symphony.digital_library.data.database.dao;

import androidx.room.Query;

import com.symphony.digital_library.data.entity.UserTakenBook;

import java.util.List;

public interface BooksByUserDao extends BaseDao<UserTakenBook> {

    @Override
    @Query("SELECT * FROM UserBookCrossRef")
    List<UserTakenBook> getAll();

}
