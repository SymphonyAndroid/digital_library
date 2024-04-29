package com.symphony.digital_library.data.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

public interface BaseDao<T> {

    List<T> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(T value);

    @Insert
    void insert(List<T> value);

    @Delete
    void delete(T value);

    @Delete
    void delete(List<T> value);

}
