package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey
    @NonNull private final String name;

    @NonNull private final String phone;

    public User(@NonNull String name, @NonNull String phone) {
        this.name = name;
        this.phone = phone;
    }

    @NonNull public String getName() {
        return name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }
}
