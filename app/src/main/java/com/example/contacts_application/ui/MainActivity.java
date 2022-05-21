package com.example.contacts_application.ui;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts_application.ContactsAdapter;
import com.example.contacts_application.R;
import com.example.contacts_application.entities.Contact;
import com.example.contacts_application.entities.User;
import com.example.contacts_application.view_model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_CONTACT_KEY = "EXTRA_CONTACT_KEY";

    private ContactViewModel contactViewModel;
    private FloatingActionButton add_fab;
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private TextView main_LBL_title;
    private ActivityResultLauncher<Intent> addContactActivityResultLauncher;
    private ActivityResultLauncher<Intent> editContactActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setActivityResultLaunchers();
        setRecyclerView();
        setViewModel();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        add_fab.setOnClickListener(view -> openAddContactActivityForResult());
    }

    /**
     * initializes the adapter and the recycler view.
     */
    private void setRecyclerView() {
        adapter = new ContactsAdapter();
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
    }


    /**
     * initialize the view model, setting the contacts for the specific user.
     */
    private void setViewModel() {
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.setUserForContacts(getUserFromLogin());
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                if (contacts.size()==0){
                    main_LBL_title.setVisibility(View.VISIBLE);
                }else{
                    main_LBL_title.setVisibility(View.INVISIBLE);
                }
                adapter.setContactList(contacts);
            }
        });
    }

    /**
     * Get the user passed from the Login activity.
     *
     * @return User from Login.
     */
    private User getUserFromLogin() {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(Activity_Login.EXTRA_USER_KEY);
        if (json != null) {
            return new Gson().fromJson(json, User.class);
        } else {
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

    /**
     * set the activity result launchers to get results from edit and add contact activities
     */
    private void setActivityResultLaunchers() {
        addContactActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        addNewContact(getContactFromActivityResult(result.getData()));
                    } else {
                        showToastMessage("New Contact did not saved!");
                    }
                });
        editContactActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        updateContact(getContactFromActivityResult(result.getData()));
                    } else {
                        showToastMessage("Edited Contact did not saved!");
                    }
                });
    }


    private void findViews() {
        recyclerView = findViewById(R.id.recyclerview);
        add_fab = findViewById(R.id.add_fab);
        main_LBL_title = findViewById(R.id.main_LBL_title);
    }


    public void openAddContactActivityForResult() {
        Intent intent = new Intent(this, Activity_Add_Contact.class);
        addContactActivityResultLauncher.launch(intent);
    }


    /**
     * Add new contact to database and set his gender.
     *
     * @param contact the new Contact to add.
     */
    private void addNewContact(Contact contact) {
        contactViewModel.setGenderForContact(contact);
        contactViewModel.insert(contact);
    }

    /**
     * update contact in database.
     *
     * @param contact the Contact to update.
     */
    private void updateContact(Contact contact) {
        contactViewModel.update(contact);
    }

    private void showToastMessage(String message) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Get the contact from the activity result.
     *
     * @param data holds the data of the contact
     * @return the Contact from the activity result or null if empty
     */
    private Contact getContactFromActivityResult(Intent data) {
        if (data != null) {
            String json = data.getStringExtra(MainActivity.EXTRA_CONTACT_KEY);
            return new Gson().fromJson(json, Contact.class);
        }
        return null;
    }


}

