package com.example.bookzoo;

import java.util.ArrayList;


public class NotificationManager {
    private static NotificationManager instance;
    private final ArrayList<NotificationItem> notifications = new ArrayList<>();

    private NotificationManager() {}

    public static synchronized NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void addNotification(String bookTitle, String bookImage) {
        notifications.add(0, new NotificationItem(bookTitle, bookImage));
    }

    public ArrayList<NotificationItem> getNotifications() {
        return notifications;
    }
}