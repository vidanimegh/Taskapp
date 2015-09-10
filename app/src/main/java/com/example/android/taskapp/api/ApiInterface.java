package com.example.android.taskapp.api;

import com.example.android.taskapp.model.Book;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by aagam shah on 8/25/2015.
 */
public interface ApiInterface {

    @GET("/index.php")
    void getBooks(Callback<List<Book>> cb);

    @GET("/book_detail.php")
    void getBook(@Query("title") String title,Callback<Book> cb);

    @POST("/add_book.php")
    void addBook(@Body Book book, Callback<Void> cb);
}
