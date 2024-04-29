package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.Book;

import java.util.List;

@Dao
public interface BookDao extends BaseDao<Book> {

    @Override
    @Query("SELECT * FROM Book")
    List<Book> getAll();

    @Query("SELECT * FROM Book WHERE id NOT IN (SELECT bookId FROM UserBookCrossRef) AND (title LIKE :query OR author LIKE :query)")
    List<Book> findByQuery(String query);


}
