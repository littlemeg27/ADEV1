package com.example.collectionviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item>
{
    public ItemAdapter(@NonNull Context context, @NonNull List<Item> items)
    {
        super(context, 0, items);
    }

    @SuppressLint("ViewHolder")
    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Item item = getItem(position);
        if (item == null)
        {
            return convertView;
        }

        TextView nameView = convertView.findViewById(R.id.item_name);
        TextView quantityView = convertView.findViewById(R.id.item_quantity);

        nameView.setText(item.getName());
        quantityView.setText(getContext().getString(R.string.quantity_text, item.getQuantity()));

        return convertView;
    }
}