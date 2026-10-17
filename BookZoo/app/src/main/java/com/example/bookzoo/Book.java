package com.example.bookzoo;

public class Book {
    private final String title;
    private final double price;
    private final String image;
    private int quantity;

    public Book(String title, double price, String image) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.quantity = 1;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}