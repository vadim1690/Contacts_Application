package com.example.contacts_application.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.contacts_application.entities.Gender;
import com.example.contacts_application.api.RetrofitService;
import com.example.contacts_application.data.AppDatabase;
import com.example.contacts_application.data.ContactDao;
import com.example.contacts_application.entities.Contact;
import com.example.contacts_application.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {

    private final ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;


    public ContactRepository(Application application) {

        this.contactDao = AppDatabase.getInstance(application).contactDao();

    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void setUserForContacts(User user) {
        this.allContacts = this.contactDao.getAlphabetizedContacts(user.getId());
    }

    public void deleteAll(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> contactDao.deleteAll(user.getId()));
    }

    public void delete(Contact id) {
        AppDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(id));
    }

    public void insert(Contact contact) {

        AppDatabase.databaseWriteExecutor.execute(() -> contactDao.insert(contact));
    }

    public void update(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> contactDao.update(contact));
    }

    /**
     * set the gender of the contact using API when response arrives update the contact in the database.
     *
     * @param contact the contact to set the gender for.
     */
    public void setGenderForContact(Contact contact) {

        RetrofitService.getInterface().getGender(getContactFirstName(contact)).enqueue(new Callback<Gender>() {
            @Override
            public void onResponse(@NonNull Call<Gender> call, @NonNull Response<Gender> response) {
                if (response.body() != null) {
                    contact.setGender(response.body().getGender());
                    update(contact);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Gender> call, @NonNull Throwable t) {
                // Nothing to do..
            }
        });
    }

    private String getContactFirstName(Contact contact) {
        if (contact.getName().contains(" "))
            return contact.getName().substring(0, contact.getName().indexOf(" "));
        return contact.getName();
    }

}
