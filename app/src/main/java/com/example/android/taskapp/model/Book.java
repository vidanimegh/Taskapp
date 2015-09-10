package com.example.android.taskapp.model;

import com.google.gson.annotations.Expose;

public class Book {

    @Expose
    public int book_id;

    @Expose
    public String author;
    @Expose
    public String categories;
    @Expose
    public String lastCheckedOut;
    @Expose
    public String lastCheckedOutBy;
    @Expose
    public String publisher;
    @Expose
    public String title;
    @Expose
    public String url;

    public Book(String title, String author, String categories, String publisher){
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.publisher = publisher;
        this.lastCheckedOut = null;
        this.lastCheckedOutBy = null;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public void setLastCheckedOut(String lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}