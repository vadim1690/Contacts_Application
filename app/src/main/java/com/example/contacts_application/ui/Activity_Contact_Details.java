package com.example.contacts_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contacts_application.R;
import com.example.contacts_application.entities.Contact;
import com.google.gson.Gson;

public class Activity_Contact_Details extends AppCompatActivity {

    private ImageButton details_BTN_exit;
    private TextView details_LBL_name;
    private TextView details_LBL_phone;

    private TextView details_LBL_gender;
    private TextView details_LBL_address;
    private TextView details_LBL_email;
    private TextView details_LBL_birthday;
    private ImageView details_IMG_gender;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        findViews();
        setOnClickListeners();

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(MainActivity.EXTRA_CONTACT_KEY);
        if (json != null) {
            contact = new Gson().fromJson(json, Contact.class);
        }
        setContactValues();
    }

    private void setContactValues() {
        details_LBL_name.setText(contact.getName());
        details_LBL_phone.setText(contact.getPhone());
        details_LBL_gender.setText(contact.getGender());
        setContactGenderIcon();
        details_LBL_address.setText(contact.getAddress());
        details_LBL_email.setText(contact.getEmail());
        details_LBL_birthday.setText(contact.getBirthday());
    }

    private void setContactGenderIcon() {
        String icName = "ic_gender_";
        if (contact.getGender() == null || (!contact.getGender().equalsIgnoreCase("male") && !contact.getGender().equalsIgnoreCase("female"))) {
            icName = icName + "none";
        } else {
            icName = icName + contact.getGender();
        }

        int drawableResourceId = this.getResources().getIdentifier(icName, "drawable", this.getPackageName());
        details_IMG_gender.setImageResource(drawableResourceId);
    }

    private void setOnClickListeners() {
        details_BTN_exit.setOnClickListener(view -> finish());
    }

    private void findViews() {
        details_BTN_exit = findViewById(R.id.details_BTN_exit);
        details_LBL_name = findViewById(R.id.details_LBL_name);
        details_LBL_phone = findViewById(R.id.details_LBL_phone);
        details_LBL_gender = findViewById(R.id.details_LBL_gender);
        details_LBL_address = findViewById(R.id.details_LBL_address);
        details_LBL_email = findViewById(R.id.details_LBL_email);
        details_LBL_birthday = findViewById(R.id.details_LBL_birthday);
        details_IMG_gender = findViewById(R.id.details_IMG_gender);
    }
}