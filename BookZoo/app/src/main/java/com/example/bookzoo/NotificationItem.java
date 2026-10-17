package com.example.bookzoo;

public class NotificationItem {
    private String bookTitle;
    private String bookImage;
    private long timestamp;

    public NotificationItem(String bookTitle, String bookImage) {
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.timestamp = System.currentTimeMillis();
    }

    public String getBookTitle() { return bookTitle; }
    public String getBookImage() { return bookImage; }
}