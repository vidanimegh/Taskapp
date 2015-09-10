package com.example.android.taskapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.android.taskapp.api.ApiInterface;
import com.example.android.taskapp.model.Book;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class BookDetailActivity extends AppCompatActivity {

    static final String API_URL = "http://192.168.0.101/books";
    RestAdapter restAdapter;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookCategories;
    TextView bookPublisher;
    TextView bookCheckout;
    Button checkoutSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);
        bookTitle = (TextView) findViewById(R.id.book_title);
        bookAuthor = (TextView) findViewById(R.id.book_author);
        bookCategories = (TextView) findViewById(R.id.book_categories);
        bookPublisher = (TextView) findViewById(R.id.book_publisher);
        bookCheckout = (TextView) findViewById(R.id.book_checkout);
        checkoutSubmit = (Button) findViewById(R.id.checkout_submit);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiInterface methods = restAdapter.create(ApiInterface.class);

        Callback<Book> cb = new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookCategories.setText("Tags: "+book.getCategories());
                bookPublisher.setText("Publisher: "+book.getPublisher());
                if(book.getLastCheckedOut() == null) {
                    bookCheckout.setText("Last Checked Out: Never");
                }
                else {
                    bookCheckout.setText("Last Checked Out: "+book.getLastCheckedOutBy()+"@"+book.getLastCheckedOut());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        };
        Intent intent = getIntent();
        methods.getBook(intent.getStringExtra("book_title"),cb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
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
