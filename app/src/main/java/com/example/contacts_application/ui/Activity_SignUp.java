package com.example.contacts_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.EditText;

import com.example.contacts_application.R;
import com.example.contacts_application.entities.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class Activity_SignUp extends AppCompatActivity {
    private EditText signUp_EDT_email;
    private EditText signUp_EDT_password;
    private MaterialButton signUp_BTN_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        signUp_BTN_signup.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(signUp_EDT_email.getText()) || TextUtils.isEmpty(signUp_EDT_password.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(Activity_Login.EXTRA_USER_KEY,
                        new Gson().toJson(
                                new User(
                                        signUp_EDT_email.getText().toString(),
                                        signUp_EDT_password.getText().toString())));
                setResult(RESULT_OK, replyIntent);

            }
            finish();
        });
    }

    private void findViews() {
        signUp_EDT_email = findViewById(R.id.signUp_EDT_email);
        signUp_EDT_password = findViewById(R.id.signUp_EDT_password);
        signUp_BTN_signup = findViewById(R.id.signUp_BTN_signup);

    }
}