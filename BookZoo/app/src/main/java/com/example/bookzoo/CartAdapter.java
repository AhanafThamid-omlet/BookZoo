package com.example.bookzoo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Book> {
    private final Context context;
    private final List<Book> books;

    public CartAdapter(Context context, List<Book> books) {
        super(context, R.layout.overview_card_layout, books);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.overview_card_layout, parent, false);
        }

        Book book = books.get(position);

        TextView titleView = convertView.findViewById(R.id.cart_book_title);
        TextView priceView = convertView.findViewById(R.id.cart_book_price);
        TextView quantityView = convertView.findViewById(R.id.cart_book_quantity);
        TextView increaseButton = convertView.findViewById(R.id.cart_book_increase);
        TextView decreaseButton = convertView.findViewById(R.id.cart_book_decrease);

        titleView.setText(book.getTitle());
        priceView.setText("$" + book.getPrice());
        quantityView.setText(String.valueOf(book.getQuantity()));

        int imageResId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            ((ImageView) convertView.findViewById(R.id.cart_book_image)).setImageResource(imageResId);
        } else {
            ((ImageView) convertView.findViewById(R.id.cart_book_image)).setImageResource(R.drawable.default_image);
        }


        increaseButton.setOnClickListener(v -> {
            book.setQuantity(book.getQuantity() + 1);
            notifyDataSetChanged();
            if (context instanceof CartActivity) {
                ((CartActivity) context).updateCartSummary();
            }
        });


        decreaseButton.setOnClickListener(v -> {
            if (book.getQuantity() > 1) {
                book.setQuantity(book.getQuantity() - 1);
                notifyDataSetChanged();
                if (context instanceof CartActivity) {
                    ((CartActivity) context).updateCartSummary();
                }
            }
        });

        convertView.findViewById(R.id.cart_book_remove).setOnClickListener(v -> {
            CartManager.getInstance().removeBook(book);
            notifyDataSetChanged();
            if (context instanceof CartActivity) {
                ((CartActivity) context).updateCartSummary();
            }
        });

        TextView orderNowButton = convertView.findViewById(R.id.orderNow);
        orderNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, No1Activity.class);
            intent.putExtra("bookTitle", book.getTitle());
            intent.putExtra("bookImage", book.getImage());
            context.startActivity(intent);
        });


        return convertView;
    }
}
