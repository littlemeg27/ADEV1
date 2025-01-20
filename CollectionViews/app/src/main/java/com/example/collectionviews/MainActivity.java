package com.example.collectionviews;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        populateItems();

        ItemAdapter adapter = new ItemAdapter(this, items);

        TextView detailName = findViewById(R.id.detail_name);
        TextView detailQuantity = findViewById(R.id.detail_quantity);
        TextView detailPrice = findViewById(R.id.detail_price);

        ListView listView = findViewById(R.id.list_view);
        Spinner spinner = findViewById(R.id.spinner_view);

        if (listView != null)
        {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) ->
            {
                Item item = items.get(position); // Get item from list
                populateDetails(item, detailName, detailQuantity, detailPrice);
            });
        }

        if (spinner != null)
        {
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    Item item = items.get(position); // Get item from list
                    populateDetails(item, detailName, detailQuantity, detailPrice);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
    }

    private void populateItems()
    {
        items.add(new Item("Apple", 10, 1.99));
        items.add(new Item("Banana", 20, 0.99));
        items.add(new Item("Orange", 15, 1.49));
        items.add(new Item("Milk", 1, 3.49));
        items.add(new Item("Bread", 25, 2.75));
        items.add(new Item("Chicken Breasts", 6, 4.99));
        items.add(new Item("Broccoli", 6, 1.79));
        items.add(new Item("Canned Tomatoes", 10, 0.89));
        items.add(new Item("Pasta", 12, 1.25));
    }

    private void populateDetails(Item item, TextView detailName, TextView detailQuantity, TextView detailPrice)
    {
        detailName.setText(item.getName());
        detailQuantity.setText(String.valueOf(item.getQuantity()));
        detailPrice.setText(String.valueOf(item.getPrice()));
    }
}
