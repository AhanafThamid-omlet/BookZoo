package com.example.bookzoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class No2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no2);

        TextView doneButton = findViewById(R.id.btn_done);
        doneButton.setOnClickListener(v -> {

            String bookTitle = getIntent().getStringExtra("bookTitle");
            String bookImage = getIntent().getStringExtra("bookImage");


            ArrayList<String> bookTitles = getIntent().getStringArrayListExtra("bookTitles");
            ArrayList<String> bookImages = getIntent().getStringArrayListExtra("bookImages");

            if (bookTitles != null && bookImages != null) {

                for (int i = 0; i < bookTitles.size(); i++) {
                    NotificationManager.getInstance().addNotification(
                            bookTitles.get(i),
                            bookImages.get(i)
                    );
                }
                Toast.makeText(this, "Orders placed successfully!", Toast.LENGTH_SHORT).show();
            } else if (bookTitle != null && bookImage != null) {

                NotificationManager.getInstance().addNotification(bookTitle, bookImage);
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            }


            if (bookTitles != null || getIntent().getBooleanExtra("fromCart", false)) {
                CartManager.getInstance().getCartItems().clear();
            }


            Intent intent = new Intent(No2Activity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}