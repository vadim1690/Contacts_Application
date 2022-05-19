package com.example.contacts_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.contacts_application.R;
import com.example.contacts_application.entities.Contact;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class Activity_Edit_Contact extends AppCompatActivity {


    private EditText editContact_EDT_birthday;
    private EditText editContact_EDT_address;
    private EditText editContact_EDT_gender;
    private EditText editContact_EDT_email;

    private TextView editContact_LBL_title;
    private EditText editContact_EDT_phoneNumber;
    private EditText editContact_EDT_name;
    private MaterialButton editContact_BTN_done;
    private ImageButton edit_BTN_exit;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(MainActivity.EXTRA_CONTACT_KEY);
        if (json != null) {
            contact = new Gson().fromJson(json, Contact.class);
        }


        findViews();
        setContactValues();
        setOnClickListeners();

    }

    private void setContactValues() {
        editContact_LBL_title.setText("Editing " + contact.getName());
        editContact_EDT_name.setText(contact.getName());
        editContact_EDT_phoneNumber.setText(contact.getPhone());
        editContact_EDT_birthday.setText(contact.getBirthday());
        editContact_EDT_address.setText(contact.getAddress());
        editContact_EDT_gender.setText(contact.getGender());
        editContact_EDT_email.setText(contact.getEmail());
    }

    private void findViews() {
        editContact_LBL_title = findViewById(R.id.editContact_LBL_title);
        editContact_EDT_phoneNumber = findViewById(R.id.editContact_EDT_phoneNumber);
        editContact_EDT_name = findViewById(R.id.editContact_EDT_name);
        editContact_BTN_done = findViewById(R.id.editContact_BTN_done);
        editContact_EDT_birthday = findViewById(R.id.editContact_EDT_birthday);
        editContact_EDT_address = findViewById(R.id.editContact_EDT_address);
        editContact_EDT_gender = findViewById(R.id.editContact_EDT_gender);
        editContact_EDT_email = findViewById(R.id.editContact_EDT_email);
        edit_BTN_exit = findViewById(R.id.edit_BTN_exit);


    }

    private void setOnClickListeners() {
        edit_BTN_exit.setOnClickListener(view -> finish());

        editContact_BTN_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (!TextUtils.isEmpty(editContact_EDT_name.getText()))
                    contact.setName(editContact_EDT_name.getText().toString());

                if (!TextUtils.isEmpty(editContact_EDT_phoneNumber.getText()))
                    contact.setPhone(editContact_EDT_phoneNumber.getText().toString());

                if (!TextUtils.isEmpty(editContact_EDT_birthday.getText()))
                    contact.setBirthday(editContact_EDT_birthday.getText().toString());

                if (!TextUtils.isEmpty(editContact_EDT_address.getText()))
                    contact.setAddress(editContact_EDT_address.getText().toString());

                if (!TextUtils.isEmpty(editContact_EDT_gender.getText()))
                    contact.setGender(editContact_EDT_gender.getText().toString());

                if (!TextUtils.isEmpty(editContact_EDT_email.getText()))
                    contact.setEmail(editContact_EDT_email.getText().toString());

                replyIntent.putExtra(MainActivity.EXTRA_CONTACT_KEY, new Gson().toJson(contact));
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}