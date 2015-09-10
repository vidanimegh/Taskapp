package com.example.android.taskapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.taskapp.api.ApiInterface;
import com.example.android.taskapp.model.Book;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class AddBookActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private static final String API_URL = "http://192.168.0.101/books";
    private EditText title;
    private EditText author;
    private EditText categories;
    private EditText publisher;
    private Button saveBook;
    private RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        title = (EditText) findViewById(R.id.title_edittext);
        author = (EditText) findViewById(R.id.author_edittext);
        categories = (EditText) findViewById(R.id.categories_edittext);
        publisher = (EditText) findViewById(R.id.publisher_edittext);
        saveBook = (Button) findViewById(R.id.save_book_button);
        saveBook.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
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

    @Override
    public void onClick(View view) {
        Book book = new Book(title.getText().toString(), author.getText().toString(), categories.getText().toString(), publisher.getText().toString());
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiInterface methods = restAdapter.create(ApiInterface.class);

        Callback<Void> cb = new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                Intent intent = getSupportParentActivityIntent();
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        };

        methods.addBook(book,cb);
    }
}
