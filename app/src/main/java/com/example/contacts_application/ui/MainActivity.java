package com.example.contacts_application.ui;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.contacts_application.ContactsAdapter;
import com.example.contacts_application.R;
import com.example.contacts_application.entities.Contact;
import com.example.contacts_application.entities.User;
import com.example.contacts_application.view_model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_CONTACT_KEY = "EXTRA_CONTACT_KEY";

    private ContactViewModel contactViewModel;
    private FloatingActionButton add_fab;
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> addContactActivityResultLauncher;
    private ActivityResultLauncher<Intent> editContactActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setActivityResultLaunchers();
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.setUserForContacts(getUser());
        contactViewModel.deleteAll(); //TODO delete this after tests

        ContactsAdapter adapter = new ContactsAdapter();
        adapter.setContactListener(new ContactsAdapter.ContactListener() {
            @Override
            public void clicked(Contact contact) {
                openContactDetailsActivity(contact);

            }

            @Override
            public void delete(Contact contact) {
                contactViewModel.delete(contact);

            }

            @Override
            public void edit(Contact contact) {
                openEditContactActivityForResult(contact);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        contactViewModel.getAllContacts().observe(this, adapter::setContactList);


        add_fab.setOnClickListener(view -> openAddContactActivityForResult());
    }

    private User getUser() {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(Activity_Login.EXTRA_USER_KEY);
        if(json!=null){
           return new Gson().fromJson(json, User.class);
        }else{
            throw new RuntimeException();
        }
    }

    private void openContactDetailsActivity(Contact contact) {
        Intent intent = new Intent(this, Activity_Contact_Details.class);
        intent.putExtra(EXTRA_CONTACT_KEY, new Gson().toJson(contact));
        startActivity(intent);
    }

    private void openEditContactActivityForResult(Contact contact) {
        Intent intent = new Intent(this, Activity_Edit_Contact.class);
        intent.putExtra(EXTRA_CONTACT_KEY, new Gson().toJson(contact));
        editContactActivityResultLauncher.launch(intent);
    }

    private void setActivityResultLaunchers() {
        addContactActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Contact contact = new Gson().fromJson(data.getStringExtra(EXTRA_CONTACT_KEY), Contact.class);
                            addNewContact(contact);
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Contact did not saved!",
                                Toast.LENGTH_LONG).show();
                    }
                });
        editContactActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String json = data.getStringExtra(MainActivity.EXTRA_CONTACT_KEY);
                            Contact contact = new Gson().fromJson(json, Contact.class);
                            updateContact(contact);
                        }
                    } else {
                        Toast.makeText(
                                MainActivity.this.getApplicationContext(),
                                "Contact did not saved!",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void findViews() {
        recyclerView = findViewById(R.id.recyclerview);
        add_fab = findViewById(R.id.add_fab);
    }


    public void openAddContactActivityForResult() {
        Intent intent = new Intent(this, Activity_Add_Contact.class);
        addContactActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed


    private void addNewContact(Contact contact) {
        contactViewModel.setGenderForContact(contact);
        contactViewModel.insert(contact);

    }

    private void updateContact(Contact contact) {
        contactViewModel.update(contact);
    }


}

