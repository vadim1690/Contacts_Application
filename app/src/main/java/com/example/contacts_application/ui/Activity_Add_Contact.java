package com.example.contacts_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.contacts_application.R;
import com.example.contacts_application.entities.Contact;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class Activity_Add_Contact extends AppCompatActivity {


    private EditText contact_EDT_birthday;
    private EditText contact_EDT_address;
    private EditText contact_EDT_email;

    private EditText contact_EDT_name;
    private EditText contact_EDT_phoneNumber;
    private MaterialButton contact_BTN_add;
    private ImageButton contact_BTN_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        findViews();
        setOnClickListeners();

    }

    private void setOnClickListeners() {

        contact_BTN_exit.setOnClickListener(view -> finish());
        contact_BTN_add.setOnClickListener(view -> addContact());
    }

    private void addContact() {
        Intent replyIntent = new Intent();
        if(!validateMandatoryFields()){
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        Contact contact = new Contact(contact_EDT_name.getText().toString(), contact_EDT_phoneNumber.getText().toString());
        setContactOptionalFields(contact);
        replyIntent.putExtra(MainActivity.EXTRA_CONTACT_KEY, new Gson().toJson(contact));
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void setContactOptionalFields(Contact contact) {
        if (!TextUtils.isEmpty(contact_EDT_address.getText()))
            contact.setAddress(contact_EDT_address.getText().toString());

        if (!TextUtils.isEmpty(contact_EDT_birthday.getText()))
            contact.setBirthday(contact_EDT_birthday.getText().toString());

        if (!TextUtils.isEmpty(contact_EDT_email.getText()))
            contact.setEmail(contact_EDT_email.getText().toString());
    }

    /**
     * check if the mandatory fields are filled.
     * @return true if the mandatory fields are filled, otherwise return false
     */
    private boolean validateMandatoryFields() {
        if (TextUtils.isEmpty(contact_EDT_name.getText())) {
            contact_EDT_name.setError("This field is mandatory");
            return false;
        }
        if (TextUtils.isEmpty(contact_EDT_phoneNumber.getText())) {
            contact_EDT_phoneNumber.setError("This field is mandatory");
            return false;
        }
        return true;
    }

    private void findViews() {
        contact_EDT_name = findViewById(R.id.contact_EDT_name);
        contact_EDT_phoneNumber = findViewById(R.id.contact_EDT_phoneNumber);
        contact_BTN_add = findViewById(R.id.contact_BTN_add);
        contact_EDT_birthday = findViewById(R.id.contact_EDT_birthday);
        contact_EDT_address = findViewById(R.id.contact_EDT_address);
        contact_EDT_email = findViewById(R.id.contact_EDT_email);
        contact_BTN_exit = findViewById(R.id.contact_BTN_exit);
    }
}