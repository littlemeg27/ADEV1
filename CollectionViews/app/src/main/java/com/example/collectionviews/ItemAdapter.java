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

// Brenna Pavlinchak
// C202501
// ItemAdapter.java

public class ItemAdapter extends ArrayAdapter<Item>
{
    private final LayoutInflater inflater;

    public ItemAdapter(Context context, List<Item> items)
    {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Item item = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(item != null ? item.getName() : "Unknown");

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Item item = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(item != null ? item.getName() : "Unknown");

        return convertView;
    }
}
