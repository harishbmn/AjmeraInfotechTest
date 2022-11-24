package com.digio.ajmerainfotechassesment.screens.author;

public
class AuthorModel {
    String author;
    Integer id;

    public AuthorModel(String author, Integer id) {
        this.author = author;
        this.id = id;
    }

    public AuthorModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
