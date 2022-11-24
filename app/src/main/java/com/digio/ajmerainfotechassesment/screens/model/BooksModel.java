package com.digio.ajmerainfotechassesment.screens.model;

public class BooksModel {
    String author;
    String books;


    public BooksModel(String author, String books) {
        this.author = author;
        this.books = books;
    }

    public BooksModel() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
