package com.example.contacts_application.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.contacts_application.entities.User;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password")
    User find(String email, String password);

    @Query("DELETE FROM users_table")
    void deleteAll();
}
