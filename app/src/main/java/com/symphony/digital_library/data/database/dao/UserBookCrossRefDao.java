package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.UserBookCrossRef;

import java.util.List;

@Dao
public interface UserBookCrossRefDao extends BaseDao<UserBookCrossRef> {

    @Override
    @Query("SELECT * FROM UserBookCrossRef")
    List<UserBookCrossRef> getAll();

    @Override
    @Query("SELECT * FROM UserBookCrossRef WHERE bookId LIKE :query OR userId LIKE :query")
    List<UserBookCrossRef> findByQuery(String query);
}
