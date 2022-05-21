package com.example.contacts_application.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.contacts_application.UserCallback;
import com.example.contacts_application.data.AppDatabase;
import com.example.contacts_application.data.UserDao;
import com.example.contacts_application.entities.User;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Application application) {
        userDao = AppDatabase.getInstance(application).userDao();
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    /**
     * find user in database, if user exist pass him to the callback.
     *
     * @param email        email of the user to find.
     * @param password     password of the user to find.
     * @param userCallback callback to pass the user to if found.
     */
    public void find(String email, String password, UserCallback userCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.find(email, password);
            handler.post(() -> userCallback.userFound(user));
        });

    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAll());
    }

}
