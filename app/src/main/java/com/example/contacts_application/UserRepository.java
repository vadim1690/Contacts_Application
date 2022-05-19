package com.example.contacts_application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.contacts_application.data.AppDatabase;
import com.example.contacts_application.data.UserDao;
import com.example.contacts_application.entities.User;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        userDao = AppDatabase.getInstance(application).userDao();
    }

    public void insert(User user){

        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void find(String email, String password, UserCallback userCallback){
        Handler handler = new Handler(Looper.getMainLooper());
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user = userDao.find(email,password);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userCallback.userFound(user);
                    }
                });
            }
        });

    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAll());
    }

}
