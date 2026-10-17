package com.example.bookzoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sign_upActivity extends AppCompatActivity {
    private TextView loginFromSignup;
    private TextView signupBtn;
    private CheckBox agreeCheckbox;
    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);

        loginFromSignup = findViewById(R.id.loginFromsignup);
        signupBtn = findViewById(R.id.signupbtn);
        agreeCheckbox = findViewById(R.id.agree);
        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);

        loginFromSignup.setOnClickListener(v -> {
            startActivity(new Intent(sign_upActivity.this, log_inActivity.class));
            finish();
        });

        signupBtn.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(sign_upActivity.this,
                        "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.addUser(username, email, password)) {
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putString("current_user_email", email);
                editor.apply();

                Toast.makeText(sign_upActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(sign_upActivity.this, log_inActivity.class));
                finish();
            }

            if (!agreeCheckbox.isChecked()) {
                Toast.makeText(sign_upActivity.this,
                        "Please agree to Privacy and Policy", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkEmail(email)) {
                Toast.makeText(sign_upActivity.this,
                        "Email already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.addUser(username, email, password)) {
                Toast.makeText(sign_upActivity.this,
                        "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(sign_upActivity.this, log_inActivity.class));
                finish();
            } else {
                Toast.makeText(sign_upActivity.this,
                        "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
