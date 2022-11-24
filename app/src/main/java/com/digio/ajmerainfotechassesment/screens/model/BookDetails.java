package com.digio.ajmerainfotechassesment.screens.model;

public
class BookDetails {
    String bookName;
    String price;
    Integer id;

    public BookDetails(String bookName, String price, Integer id) {
        this.bookName = bookName;
        this.price = price;
        this.id = id;
    }

    public BookDetails() {

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
