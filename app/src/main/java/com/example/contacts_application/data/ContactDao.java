package com.example.contacts_application.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contacts_application.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contacts_table WHERE userId = :userId ")
    void deleteAll(Long userId);


    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts_table WHERE userId = :userId ORDER BY name ASC")
    LiveData<List<Contact>> getAlphabetizedContacts(Long userId);

    @Update
    void update(Contact contact);
}
