package com.symphony.digital_library.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.data.entity.UserTakenBook;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<User> {

    @Override
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE name LIKE :name")
    List<User> findUsersByName(String name);

    @Query("SELECT * FROM User WHERE name = :name")
    User findUserByName(String name);

    @Query("SELECT * FROM User WHERE name = :name")
    UserTakenBook booksByUser(String name);

}
