package com.example.android.taskapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.android.taskapp.api.ApiInterface;
import com.example.android.taskapp.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BookListActivity extends AppCompatActivity {

    static final String API_URL = "http://192.168.0.104:8080/books";
    ListView books_listview;
    RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        books_listview = (ListView) findViewById(R.id.books_listview);

        restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        ApiInterface methods = restAdapter.create(ApiInterface.class);

        Callback cb = new Callback() {
            @Override
            public void success(Object o, Response response) {
                List<Book> books = (List<Book>) o;
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                List<HashMap<String,Object>> bookMapList = new ArrayList<>();
                for(Book b: books){
                    HashMap<String, Object> bookmap = new HashMap<>();

                    try {
                        bookmap.put(b.getClass().getField("author").getName(),b.getAuthor());
                        bookmap.put(b.getClass().getField("categories").getName(),b.getCategories());
                        bookmap.put(b.getClass().getField("lastCheckedOut").getName(),b.getLastCheckedOut());
                        bookmap.put(b.getClass().getField("lastCheckedOutBy").getName(),b.getLastCheckedOutBy());
                        bookmap.put(b.getClass().getField("publisher").getName(),b.getPublisher());
                        bookmap.put(b.getClass().getField("title").getName(),b.getTitle());
                        bookmap.put(b.getClass().getField("url").getName(),b.getUrl());
                        bookMapList.add(bookmap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), bookMapList, R.layout.list_item_book,
                        new String [] {"title", "author"},new int [] {R.id.book_title, R.id.book_author});

                books_listview.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("BookListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getBooks(cb);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
