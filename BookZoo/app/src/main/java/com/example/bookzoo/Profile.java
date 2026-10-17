package com.example.bookzoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    private TextView profileName, profileEmail;
    private String currentUserEmail; // Store current user's email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(Profile.this, HomeActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.AddCart) {
                startActivity(new Intent(Profile.this, CartActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.profile) {

                Intent intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });


        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        TextView editProfileBtn = findViewById(R.id.editProfile);


        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentUserEmail = prefs.getString("current_user_email", "");


        loadUserDetails();


        editProfileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, ProfileEdit.class);
            intent.putExtra("current_email", currentUserEmail);
            startActivity(intent);
        });


        TextView cartButton = findViewById(R.id.profileMyCart);
        cartButton.setOnClickListener(v -> {
            startActivity(new Intent(Profile.this, CartActivity.class));
        });


        TextView notificationButton = findViewById(R.id.profileNotification);
        notificationButton.setOnClickListener(v -> {
            startActivity(new Intent(Profile.this, Notification.class));
        });

        TextView logoutButton = findViewById(R.id.profileLogout);
        logoutButton.setOnClickListener(v -> {

            SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();


            Intent intent = new Intent(Profile.this, welcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getUserDetails(currentUserEmail);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            profileName.setText(username);
            profileEmail.setText(email);
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserDetails();
    }

}