package com.example.assignment6.interfaces;

import com.example.assignment6.model.Posts;
import com.example.assignment6.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://jsonplaceholder.typicode.com/";
      @GET("/users")
    Call <List<Users>> getAllUsers();
      @GET("/posts")
    Call<List<Posts>> getPosts(@Query("userId") String userId);
}
