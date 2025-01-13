package com.example.lettercounting;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.Collections;

// Brenna Pavlinchak
// Term C202501
// Main Activity

public class MainActivity extends AppCompatActivity
{

    private EditText inputField;
    private Button addButton, viewButton;
    private TextView averageText, medianText;
    private NumberPicker indexPicker;
    private ArrayList<String> stringCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.editText_input);
        addButton = findViewById(R.id.button_add);
        viewButton = findViewById(R.id.button_view);
        averageText = findViewById(R.id.textView_average);
        medianText = findViewById(R.id.textView_median);
        indexPicker = findViewById(R.id.numberPicker);

        stringCollection = new ArrayList<>();

        addButton.setOnClickListener(v -> addString());
        viewButton.setOnClickListener(v -> viewString());

        updateNumberPicker();
        updateStatistics();
    }

    private void addString()
    {
        String input = inputField.getText().toString().trim();

        if (input.isEmpty())
        {
            showToast("Input cannot be empty or spaces only.");
            return;
        }

        if (stringCollection.contains(input))
        {
            showToast("Input already exists in the collection.");
            return;
        }

        stringCollection.add(input);
        inputField.setText("");
        updateNumberPicker();
        updateStatistics();
        showToast("String added successfully.");
    }

    private void viewString()
    {
        if (stringCollection.isEmpty())
        {
            showToast("The collection is empty.");
            return;
        }

        int index = indexPicker.getValue();
        String selectedString = stringCollection.get(index);

        new AlertDialog.Builder(this)
                .setTitle("Selected String")
                .setMessage(selectedString)
                .setPositiveButton("Close", null)
                .setNegativeButton("Remove", (dialog, which) ->
                {
                    stringCollection.remove(index);
                    updateNumberPicker();
                    updateStatistics();
                    showToast("String removed successfully.");
                })
                .show();
    }

    private void updateNumberPicker()
    {
        indexPicker.setMinValue(0);
        indexPicker.setMaxValue(Math.max(0, stringCollection.size() - 1));
        indexPicker.setWrapSelectorWheel(false);
    }

    private void updateStatistics()
    {
        if (stringCollection.isEmpty())
        {
            averageText.setText("N/A");
            medianText.setText("N/A");
            return;
        }

        double average = stringCollection.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);
        averageText.setText(String.format("%.2f", average));

        ArrayList<Integer> lengths = new ArrayList<>();
        for (String s : stringCollection)
        {
            lengths.add(s.length());
        }
        Collections.sort(lengths);
        double median;
        int size = lengths.size();

        if (size % 2 == 0)
        {
            median = (lengths.get(size / 2 - 1) + lengths.get(size / 2)) / 2.0;
        }
        else
        {
            median = lengths.get(size / 2);
        }
        medianText.setText(String.format("%.2f", median));
    }

    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
