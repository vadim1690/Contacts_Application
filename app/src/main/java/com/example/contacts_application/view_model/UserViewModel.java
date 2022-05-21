package com.example.contacts_application.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.example.contacts_application.UserCallback;
import com.example.contacts_application.repositories.UserRepository;
import com.example.contacts_application.entities.User;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }
    public void insert(User user){
        userRepository.insert(user);
    }

    public void find(String email , String password, UserCallback userCallback){
         userRepository.find(email,password,userCallback);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }
}
