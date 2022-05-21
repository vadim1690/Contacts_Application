package com.example.contacts_application.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contacts_application.repositories.ContactRepository;
import com.example.contacts_application.entities.Contact;
import com.example.contacts_application.entities.User;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;
    private User user;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);

    }

    /**
     * set the user that currently logged in to show his contacts only
     * @param user the user currently logged in.
     */
    public void setUserForContacts(User user) {
        this.user = user;
        contactRepository.setUserForContacts(user);
        allContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact){
        contact.setUserId(user.getId());
        contactRepository.insert(contact);
    }
    public void deleteAll(){
        contactRepository.deleteAll(user);
    }
    public void delete(Contact contact){
        contactRepository.delete(contact);
    }

    public void update(Contact contact) {
        contactRepository.update(contact);
    }
    public void setGenderForContact(Contact contact){
        contactRepository.setGenderForContact(contact);
    }


}
