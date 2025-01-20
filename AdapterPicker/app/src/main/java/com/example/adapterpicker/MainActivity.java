package com.example.adapterpicker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Person> personList;
    private ListView listView;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner viewSpinner = findViewById(R.id.viewSpinner);
        Spinner adapterSpinner = findViewById(R.id.adapterSpinner);
        listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.gridView);

        initializePersonList();

        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }
                else
                {
                    listView.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Handle adapter spinner selection
        adapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0: // ArrayAdapter
                        setupArrayAdapter();
                        break;
                    case 1: // SimpleAdapter
                        setupSimpleAdapter();
                        break;
                    case 2: // BaseAdapter
                        setupBaseAdapter();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initializePersonList()
    {
        personList = new ArrayList<>();

        String firstName = getString(R.string.first_name);
        String lastName = getString(R.string.last_name);
        String birthday = getString(R.string.birthday);
        personList.add(new Person(firstName, lastName, birthday, R.drawable.placeholder));

        personList.add(new Person("Emma", "Johnson", "02/14/1990", R.drawable.emma));
        personList.add(new Person("Liam", "Carter", "08/23/1985", R.drawable.liam));
        personList.add(new Person("Sophia", "Martinez", "04/10/1992", R.drawable.sophia));
        personList.add(new Person("Noah", "Williams", "06/18/1988", R.drawable.noah));
        personList.add(new Person("Ava", "Brown", "12/03/1995", R.drawable.ava));
    }

    private void setupArrayAdapter()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                getPersonNames());
        listView.setAdapter(adapter);
        gridView.setAdapter(adapter);
    }

    private void setupSimpleAdapter()
    {
        List<HashMap<String, String>> data = new ArrayList<>();
        for (Person person : personList)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("line1", person.getFirstName() + " " + person.getLastName());
            map.put("line2", person.getBirthday());
            data.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this, data, R.layout.simple_item,
                new String[]{"line1", "line2"},
                new int[]{R.id.line1, R.id.line2});
        listView.setAdapter(adapter);
        gridView.setAdapter(adapter);
    }

    private void setupBaseAdapter()
    {
        PersonAdapter adapter = new PersonAdapter(this, personList);
        listView.setAdapter(adapter);
        gridView.setAdapter(adapter);
    }

    private List<String> getPersonNames()
    {
        List<String> names = new ArrayList<>();
        for (Person person : personList)
        {
            names.add(person.getFirstName() + " " + person.getLastName());
        }
        return names;
    }
}
