package com.example.contacts_application.api;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenderApi {
    @GET(".")
    Call<Gender> getGender(@Query("name") String name);
}
