package com.example.bookzoo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {

                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.AddCart) {

                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(HomeActivity.this, Profile.class));
                finish();
                return true;
            }
            return false;
        });

        TextView notificationBell = findViewById(R.id.notification_bell);
        notificationBell.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, Notification.class));
        });

        gridLayout = findViewById(R.id.grid_layout);
        dbHelper = new DatabaseHelper(this);

        findViewById(R.id.filter_programming).setOnClickListener(v -> loadBooksByCategory("Programming"));
        findViewById(R.id.filter_mystery).setOnClickListener(v -> loadBooksByCategory("Mystery"));
        findViewById(R.id.filter_history).setOnClickListener(v -> loadBooksByCategory("History"));
        findViewById(R.id.filter_autobiography).setOnClickListener(v -> loadBooksByCategory("Autobiography"));
        findViewById(R.id.filter_politics).setOnClickListener(v -> loadBooksByCategory("Politics"));
        findViewById(R.id.filter_biographies).setOnClickListener(v -> loadBooksByCategory("Biographies"));

        loadBooksFromDatabase();
    }

    private void loadBooksByCategory(String category) {
        Cursor cursor = dbHelper.getBooksByCategory(category);
        populateBooks(cursor);
    }

    private void populateBooks(Cursor cursor) {
        gridLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("title");
                int priceIndex = cursor.getColumnIndex("price");
                int imageIndex = cursor.getColumnIndex("image");

                String title = cursor.getString(titleIndex);
                double price = cursor.getDouble(priceIndex);
                String imageName = cursor.getString(imageIndex);

                View bookCard = inflater.inflate(R.layout.book_card_layout, null);

                TextView titleView = bookCard.findViewById(R.id.book_title);
                TextView priceView = bookCard.findViewById(R.id.book_price);
                ImageView imageView = bookCard.findViewById(R.id.book_image);
                TextView addToCartButton = bookCard.findViewById(R.id.add_to_cart_button);

                titleView.setText(title);
                priceView.setText("$" + price);

                int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imageResId != 0) {
                    imageView.setImageResource(imageResId);
                } else {
                    imageView.setImageResource(R.drawable.default_image);
                }

                Book book = new Book(title, price, imageName);

                addToCartButton.setOnClickListener(v -> {
                    CartManager.getInstance().addBook(book);
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                });

                imageView.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeActivity.this, OverviewActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("price", price);
                    intent.putExtra("image", imageName);
                    startActivity(intent);
                });


                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = getResources().getDimensionPixelSize(R.dimen.book_card_width);
                params.height = getResources().getDimensionPixelSize(R.dimen.book_card_height);
                params.setMargins(8, 8, 8, 8);
                bookCard.setLayoutParams(params);

                gridLayout.addView(bookCard);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    private void loadBooksFromDatabase() {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getAllBooks();
            LayoutInflater inflater = LayoutInflater.from(this);

            int count = 0;

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int priceIndex = cursor.getColumnIndex("price");
                    int imageIndex = cursor.getColumnIndex("image");

                    if (idIndex >= 0 && titleIndex >= 0 && priceIndex >= 0 && imageIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String title = cursor.getString(titleIndex);
                        double price = cursor.getDouble(priceIndex);
                        String imageName = cursor.getString(imageIndex);


                        Log.d("BOOK_DEBUG", "ID:" + id + ", Title: " + title + ", Price: $" + price + ", Image: " + imageName);

                        count++;


                        View bookCard = inflater.inflate(R.layout.book_card_layout, null);

                        TextView titleView = bookCard.findViewById(R.id.book_title);
                        TextView priceView = bookCard.findViewById(R.id.book_price);
                        ImageView imageView = bookCard.findViewById(R.id.book_image);
                        TextView addToCartButton = bookCard.findViewById(R.id.add_to_cart_button);

                        titleView.setText(title);
                        priceView.setText("$" + price);

                        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        if (imageResId != 0) {
                            imageView.setImageResource(imageResId);
                        } else {
                            imageView.setImageResource(R.drawable.default_image);
                        }


                        Book book = new Book(title, price, imageName);


                        addToCartButton.setOnClickListener(v -> {
                            CartManager.getInstance().addBook(book);
                            Log.d("CART_DEBUG", "Book added to cart: " + book.getTitle());
                            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                            startActivity(intent);
                        });

                        imageView.setOnClickListener(v -> {
                            Intent intent = new Intent(HomeActivity.this, OverviewActivity.class);
                            intent.putExtra("title", title);
                            intent.putExtra("price", price);
                            intent.putExtra("image", imageName);
                            startActivity(intent);
                        });

                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.width = getResources().getDimensionPixelSize(R.dimen.book_card_width);
                        params.height = getResources().getDimensionPixelSize(R.dimen.book_card_height);
                        params.setMargins(8, 8, 8, 8);
                        bookCard.setLayoutParams(params);

                        gridLayout.addView(bookCard);
                    }
                } while (cursor.moveToNext());
            }

            Log.d("BOOK_DEBUG", "Total books loaded from DB: " + count);
        } catch (Exception e) {
            Log.e("BOOK_DEBUG", "Error loading books from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}