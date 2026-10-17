package com.example.bookzoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationItem> {
    private final Context context;

    public NotificationAdapter(Context context, ArrayList<NotificationItem> notifications) {
        super(context, 0, notifications);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.notification_item, parent, false);
        }

        NotificationItem item = getItem(position);
        ImageView bookImage = convertView.findViewById(R.id.bookThumbnail);
        TextView bookTitle = convertView.findViewById(R.id.titleText);

        bookTitle.setText(item.getBookTitle());


        int imageResId = context.getResources().getIdentifier(
                item.getBookImage(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            bookImage.setImageResource(imageResId);
        } else {
            bookImage.setImageResource(R.drawable.default_image);
        }

        return convertView;
    }
}