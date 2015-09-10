package com.example.android.taskapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.android.taskapp.api.ApiInterface;
import com.example.android.taskapp.model.Book;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class BookListActivity extends AppCompatActivity {

    static final String API_URL = "http://192.168.0.101/books";
    ListView books_listview;
    RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        books_listview = (ListView) findViewById(R.id.books_listview);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000,TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiInterface methods = restAdapter.create(ApiInterface.class);

        Callback<List<Book>> cb = new Callback<List<Book>>() {
            @Override
            public void success(List<Book> books, Response response) {
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                List<HashMap<String,Object>> bookMapList = new ArrayList<>();
                for(Book b: books){
                    HashMap<String, Object> bookmap = new HashMap<>();

                    try {

                        bookmap.put(b.getClass().getField("book_id").getName(),b.getBook_id());
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

        books_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HashMap<String, Object> cursor = (HashMap<String, Object>) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getApplication(), BookDetailActivity.class);
                intent.putExtra("book_title", (String) cursor.get("title"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }
        });
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
        if (id == R.id.action_add) {
            Intent intent = new Intent(this,AddBookActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
