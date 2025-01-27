package com.example.googlebookslisting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//Brenna Pavlinchak
//C202501
//MainActivity.java

public class MainActivity extends AppCompatActivity
{
    private ProgressBar progressBar;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        gridView = findViewById(R.id.gridView);

        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=bill+bryson";
        new GetBooksTask().execute(apiUrl);
    }

    private class GetBooksTask extends AsyncTask<String, Void, List<Book>>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Book> doInBackground(String... strings)
        {
            List<Book> books = new ArrayList<>();
            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }

                String jsonResponse = result.toString();
                JSONObject root = new JSONObject(jsonResponse);
                JSONArray items = root.getJSONArray("items");

                for (int i = 0; i < items.length(); i++)
                {
                    JSONObject bookObj = items.getJSONObject(i);
                    JSONObject volumeInfo = bookObj.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");

                    String thumbnailUrl = volumeInfo.has("imageLinks")
                            ? volumeInfo.getJSONObject("imageLinks").getString("thumbnail")
                            : null;

                    books.add(new Book(title, thumbnailUrl));
                }

                inputStream.close();
                connection.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books)
        {
            super.onPostExecute(books);
            progressBar.setVisibility(View.GONE);
            BookAdapter adapter = new BookAdapter(MainActivity.this, books);
            gridView.setAdapter(adapter);
        }
    }
}