package com.example.contacts_application.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.Toast;

import com.example.contacts_application.R;


import com.example.contacts_application.entities.User;
import com.example.contacts_application.view_model.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class Activity_Login extends AppCompatActivity {
    public static final String EXTRA_USER_KEY = "EXTRA_USER_KEY";
    private MaterialButton login_BTN_signup;
    private MaterialButton login_BTN_login;
    private EditText login_EDT_password;
    private EditText login_EDT_email;
    private UserViewModel userViewModel;

    private ActivityResultLauncher<Intent> signUpActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setClickListeners();
        setActivityResultLaunchers();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    /**
     *  set the sign up activity result to create a new user if the result is OK.
     */
    private void setActivityResultLaunchers() {
        signUpActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            User user = new Gson().fromJson(data.getStringExtra(EXTRA_USER_KEY), User.class);
                            addNewUser(user);
                        }
                    } else {
                        showToastMessage("User did not saved!");

                    }
                });
    }

    private void setClickListeners() {
        login_BTN_login.setOnClickListener(view -> startMainActivity());
        login_BTN_signup.setOnClickListener(view -> startSignUpActivity());

    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, Activity_SignUp.class);
        signUpActivityResultLauncher.launch(intent);
    }

    /**
     *  add new user to database if the user is not already exists.
     * @param newUser the new user to add.
     */
    private void addNewUser(User newUser) {
        userViewModel.find(newUser.getEmail(), newUser.getPassword(), user -> {
            if (user != null) {
                showToastMessage("User already exists!");

            } else {
                showToastMessage("User created successfully");
                userViewModel.insert(newUser);
            }
        });

    }

    /**
     *  start the main activity if login details are correct.
     */
    private void startMainActivity() {

        userViewModel.find(login_EDT_email.getText().toString(), login_EDT_password.getText().toString(), user -> {
            if (user == null) {
                showToastMessage("User does not exist!");
            } else {
                showToastMessage("Login successfully");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_USER_KEY, new Gson().toJson(user));
                startActivity(intent);
                finish();
            }
        });


    }

    private void showToastMessage(String message) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void findViews() {
        login_BTN_signup = findViewById(R.id.login_BTN_signup);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_EDT_email = findViewById(R.id.login_EDT_email);

    }
}