package com.example.contacts_application.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "contacts_table",foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId",onDelete = CASCADE)})
public class Contact {


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "phone_number")
    private String phone;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "userId")
    private Long userId;


    public Contact(@NonNull String name, @NonNull String phone ) {
        this.name = name;
        this.phone = phone;
        id = UUID.randomUUID().toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }


    @NonNull
    public String getName() {
        return name;
    }


    @NonNull
    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }
}
