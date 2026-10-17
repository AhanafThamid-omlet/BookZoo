package com.example.bookzoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView cartListView = findViewById(R.id.cart_list);
        List<Book> cartItems = CartManager.getInstance().getCartItems();

        CartAdapter adapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(adapter);

        updateCartSummary();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_cart);
        bottomNavigationView.setSelectedItemId(R.id.AddCart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.AddCart) {

                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(CartActivity.this, Profile.class));
                finish();
                return true;
            }
            return false;
        });

        TextView buyNowButton = findViewById(R.id.buy_now_from_cart);
        buyNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, No1Activity.class);

            ArrayList<String> bookTitles = new ArrayList<>();
            ArrayList<String> bookImages = new ArrayList<>();
            for (Book book : cartItems) {
                bookTitles.add(book.getTitle());
                bookImages.add(book.getImage());
            }
            intent.putStringArrayListExtra("bookTitles", bookTitles);
            intent.putStringArrayListExtra("bookImages", bookImages);
            startActivity(intent);
        });
    }


    public void updateCartSummary() {
        List<Book> cartItems = CartManager.getInstance().getCartItems();
        double subtotal = 0.0;

        for (Book book : cartItems) {
            subtotal += book.getPrice() * book.getQuantity();
        }

        double tax = 2;
        double deliveryCharge = 5;
        double total = subtotal + tax + deliveryCharge;

        TextView subtotalText = findViewById(R.id.subtotal_amount);
        TextView totalText = findViewById(R.id.total_amount);

        subtotalText.setText(String.format("$%.2f", subtotal));
        totalText.setText(String.format("$%.2f", total));
    }
}
