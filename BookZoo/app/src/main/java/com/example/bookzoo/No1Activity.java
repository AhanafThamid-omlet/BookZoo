package com.example.bookzoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class No1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no1);

        String bookTitle = getIntent().getStringExtra("bookTitle");
        String bookImage = getIntent().getStringExtra("bookImage");
        ArrayList<String> bookTitles = getIntent().getStringArrayListExtra("bookTitles");
        ArrayList<String> bookImages = getIntent().getStringArrayListExtra("bookImages");

        EditText cardNumberInput = findViewById(R.id.et_card_number);
        EditText expiryInput = findViewById(R.id.et_expiry);
        EditText cvvInput = findViewById(R.id.et_cvv);
        EditText nameInput = findViewById(R.id.et_name);
        CheckBox termsCheckbox = findViewById(R.id.checkbox_terms);

        TextView confirmBtn = findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(v -> {
            String cardNumber = cardNumberInput.getText().toString().trim();
            String expiry = expiryInput.getText().toString().trim();
            String cvv = cvvInput.getText().toString().trim();
            String name = nameInput.getText().toString().trim();

            if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please fill all payment details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(No1Activity.this, No2Activity.class);

            if (bookTitles != null && bookImages != null) {
                intent.putStringArrayListExtra("bookTitles", bookTitles);
                intent.putStringArrayListExtra("bookImages", bookImages);
                intent.putExtra("fromCart", true);
            } else {
                intent.putExtra("bookTitle", bookTitle);
                intent.putExtra("bookImage", bookImage);
                intent.putExtra("fromCart", false);
            }

            startActivity(intent);
            finish();
        });
    }
}