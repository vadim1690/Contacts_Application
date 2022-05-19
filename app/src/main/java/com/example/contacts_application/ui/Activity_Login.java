package com.example.contacts_application.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contacts_application.R;
import com.example.contacts_application.UserCallback;
import com.example.contacts_application.entities.Contact;
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
        //userViewModel.deleteAll();
    }

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
                        Toast.makeText(
                                getApplicationContext(),
                                "User did not saved!",
                                Toast.LENGTH_LONG).show();
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

    private void addNewUser(User user) {
        userViewModel.insert(user);
    }

    private void startMainActivity() {

        userViewModel.find(login_EDT_email.getText().toString(), login_EDT_password.getText().toString(), new UserCallback() {
            @Override
            public void userFound(User user) {
                if(user==null){
                    Toast.makeText(
                            getApplicationContext(),
                            "User does not exist!!!",
                            Toast.LENGTH_LONG).show();
                }else{

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(EXTRA_USER_KEY , new Gson().toJson(user));
                    startActivity(intent);
                    finish();
                }
            }
        });



    }

    private void findViews() {
        login_BTN_signup = findViewById(R.id.login_BTN_signup);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_EDT_email = findViewById(R.id.login_EDT_email);

    }
}