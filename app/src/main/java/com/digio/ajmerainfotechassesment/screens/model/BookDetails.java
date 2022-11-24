package com.digio.ajmerainfotechassesment.screens.model;

public
class BookDetails {
    String bookName;
    String price;

    public BookDetails(String bookName, String price) {
        this.bookName = bookName;
        this.price = price;
    }
    public BookDetails() {

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
