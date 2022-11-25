package com.digio.ajmerainfotechassesment.screens.model;

public
class BookDetails {
    String authorName;
    String bookName;
    String price;
    Integer id;

    public BookDetails(String bookName, String price, Integer id,String authorName) {
        this.bookName = bookName;
        this.price = price;
        this.id = id;
        this.authorName = authorName;
    }

    public BookDetails() {

    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
