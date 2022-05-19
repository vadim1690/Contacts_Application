package com.example.contacts_application;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts_application.data.AppDatabase;
import com.example.contacts_application.entities.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> mContactList =  new ArrayList<>();
    private ContactListener contactListener;

    public ContactsAdapter() {

    }

    public void setContactListener(ContactListener contactListener) {
        this.contactListener = contactListener;
    }

    public void setContactList(final List<Contact> contactList) {
        if (mContactList == null) {
            mContactList = contactList;
            notifyItemRangeInserted(0, contactList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mContactList.size();
                }

                @Override
                public int getNewListSize() {
                    return contactList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mContactList.get(oldItemPosition).getId(), contactList.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Contact newContact = contactList.get(newItemPosition);
                    Contact oldContact = mContactList.get(oldItemPosition);
                    return Objects.equals(newContact.getId(), oldContact.getId())
                            && TextUtils.equals(newContact.getName(), oldContact.getName())
                            && TextUtils.equals(newContact.getPhone(), oldContact.getPhone());
                }
            });
            mContactList = contactList;
            result.dispatchUpdatesTo(this);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        Contact contact = mContactList.get(position);
        contactViewHolder.list_item_name.setText(contact.getName());
        contactViewHolder.list_item_phone.setText(contact.getPhone());


    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public Contact getItem(int position) {
        return mContactList.get(position);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView list_item_name;
        private TextView list_item_phone;

        private ImageButton list_BTN_delete;
        private ImageButton list_BTN_edit;

        public ContactViewHolder(View itemView) {
            super(itemView);
            list_item_name = itemView.findViewById(R.id.list_item_name);
            list_item_phone = itemView.findViewById(R.id.list_item_phone);
            list_BTN_delete = itemView.findViewById(R.id.list_BTN_delete);
            list_BTN_edit = itemView.findViewById(R.id.list_BTN_edit);



            list_BTN_delete.setOnClickListener(view -> contactListener.delete(getItem(getAdapterPosition())));
            list_BTN_edit.setOnClickListener(view -> contactListener.edit(getItem(getAdapterPosition())));
            itemView.setOnClickListener(view -> contactListener.clicked(getItem(getAdapterPosition())));
        }



    }

    public interface ContactListener {
        void clicked(Contact contact);
        void delete(Contact contact);
        void edit(Contact contact);
    }
}


