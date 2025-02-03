package com.example.smoothrotationchanges;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String KEY_STRING_LIST = "stringList";
    private static final String KEY_EDIT_TEXT = "editTextValue";

    private ArrayList<String> stringList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextInput = findViewById(R.id.editText_input);
        Button buttonSubmit = findViewById(R.id.button_submit);
        ListView listViewStrings = findViewById(R.id.listView_strings);

        stringList = new ArrayList<>();

        if (savedInstanceState != null)
        {
            stringList = savedInstanceState.getStringArrayList(KEY_STRING_LIST);
            editTextInput.setText(savedInstanceState.getString(KEY_EDIT_TEXT, ""));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList);
        listViewStrings.setAdapter(adapter);

        buttonSubmit.setOnClickListener(v -> addStringToList(editTextInput));
    }

    private void addStringToList(EditText editTextInput)
    {
        String inputText = editTextInput.getText().toString().trim();

        if (inputText.isEmpty())
        {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show();
        }
        else
        {
            stringList.add(inputText);
            adapter.notifyDataSetChanged();
            editTextInput.setText("");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_STRING_LIST, stringList);
        outState.putString(KEY_EDIT_TEXT, ((EditText) findViewById(R.id.editText_input)).getText().toString());
    }
}