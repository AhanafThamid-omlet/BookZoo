package com.example.bookzoo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OverviewActivity extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


        ImageView bookImage = findViewById(R.id.book_image);
        TextView bookTitle = findViewById(R.id.book_title_overview);
        TextView bookOverview = findViewById(R.id.book_overview);
        TextView addToCartButton = findViewById(R.id.add_to_cart_button_overview);


        String title = getIntent().getStringExtra("title");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String imageName = getIntent().getStringExtra("image");


        String description = fetchBookDescription(title);


        bookTitle.setText(title);
        bookOverview.setText(description);

        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            bookImage.setImageResource(imageResId);
        } else {
            bookImage.setImageResource(R.drawable.default_image);
        }


        Book book = new Book(title, price, imageName);


        addToCartButton.setOnClickListener(v -> {
            CartManager.getInstance().addBook(book);
            Log.d("CART_DEBUG", "Book added to cart: " + book.getTitle());
            Intent intent = new Intent(OverviewActivity.this, CartActivity.class);
            startActivity(intent);
        });

        TextView buyNowButton = findViewById(R.id.buy_now_overView);
        buyNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(OverviewActivity.this, No1Activity.class);
            intent.putExtra("bookTitle", title);
            intent.putExtra("bookImage", imageName);
            startActivity(intent);
        });


    }



    @SuppressLint("Range")
    private String fetchBookDescription(String title) {
        String description = "No description available.";
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT description FROM books WHERE title = ?", new String[]{title});
            if (cursor.moveToFirst()) {
                description = cursor.getString(cursor.getColumnIndex("description"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return description;
    }
}