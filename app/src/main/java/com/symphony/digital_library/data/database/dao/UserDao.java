package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<User> {

    @Override
    @Query("SELECT * FROM User")
    List<User> getAll();

}
