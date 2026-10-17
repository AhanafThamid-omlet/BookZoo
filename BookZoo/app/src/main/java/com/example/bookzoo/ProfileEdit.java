package com.example.bookzoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileEdit extends AppCompatActivity {
    private EditText fullNameInput, emailInput, passwordInput;
    private String currentEmail;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        dbHelper = new DatabaseHelper(this);

        fullNameInput = findViewById(R.id.full_name);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);

        currentEmail = getIntent().getStringExtra("current_email");


        loadUserDetails();

        TextView saveBtn = findViewById(R.id.btn_Save);
        saveBtn.setOnClickListener(v -> saveProfile());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void loadUserDetails() {
        Cursor cursor = dbHelper.getUserDetails(currentEmail);
        if (cursor.moveToFirst()) {
            fullNameInput.setText(cursor.getString(cursor.getColumnIndex("username")));
            emailInput.setText(cursor.getString(cursor.getColumnIndex("email")));
            passwordInput.setText(cursor.getString(cursor.getColumnIndex("password")));
        }
        cursor.close();
    }

    private void saveProfile() {
        String newUsername = fullNameInput.getText().toString().trim();
        String newEmail = emailInput.getText().toString().trim();
        String newPassword = passwordInput.getText().toString().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        if (dbHelper.updateUserProfile(currentEmail, newUsername, newEmail, newPassword)) {

            SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
            editor.putString("current_user_email", newEmail);
            editor.apply();

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}