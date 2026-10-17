package com.example.bookzoo;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView backButton = findViewById(R.id.topBackButton);
        backButton.setOnClickListener(v -> finish());

        ListView notificationList = findViewById(R.id.notificationList);
        NotificationAdapter adapter = new NotificationAdapter(this,
                NotificationManager.getInstance().getNotifications());
        notificationList.setAdapter(adapter);
    }
}