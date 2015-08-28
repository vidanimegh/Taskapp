package com.example.android.taskapp.api;

import com.example.android.taskapp.model.Book;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by aagam shah on 8/25/2015.
 */
public interface ApiInterface {

    @GET("/index.php")
    void getBooks(Callback<List<Book>> cb);
}
