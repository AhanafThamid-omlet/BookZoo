package com.example.bookzoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class log_inActivity extends AppCompatActivity {
    private TextView signupFromLogin;
    private TextView loginBtn;
    private EditText emailInput;
    private EditText passwordInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dbHelper = new DatabaseHelper(this);

        signupFromLogin = findViewById(R.id.signupfromlogin);
        loginBtn = findViewById(R.id.loginbtn);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);

        signupFromLogin.setOnClickListener(v -> {
            startActivity(new Intent(log_inActivity.this, sign_upActivity.class));
            finish();
        });

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(log_inActivity.this,
                        "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putString("current_user_email", email);
                editor.apply();

                Toast.makeText(log_inActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(log_inActivity.this, HomeActivity.class));
                finish();
            }

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(log_inActivity.this,
                        "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(log_inActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(log_inActivity.this,
                        "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
