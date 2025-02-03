package com.example.kotlincoroutines;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.CoroutineStart;
import java.util.List;

//Brenna Pavlinchak
//C202501
//MainActivity.java

public class MainActivity extends AppCompatActivity
{
    private ProgressBar progressBar;
    private GridView gridView;
    private BookAdapter bookAdapter;
    private String apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progressBar);

        apiUrl = "https://www.googleapis.com/books/v1/volumes?q=bill+bryson";

        fetchBooks();
    }

    private void fetchBooks()
    {
        progressBar.setVisibility(View.VISIBLE);

        GlobalScope.INSTANCE.launch(Dispatchers.getMain(), CoroutineStart.DEFAULT, (coroutineScope, continuation) ->
        {
            try
            {
                List<com.example.googlebookslisting.Book> books = (List<com.example.googlebookslisting.Book>) BookFetcher.INSTANCE.fetchBooks(apiUrl);
                runOnUiThread(() ->
                {
                    progressBar.setVisibility(View.GONE);
                    bookAdapter = new BookAdapter(MainActivity.this, books);
                    gridView.setAdapter(bookAdapter);
                });
            }
            catch (Exception e)
            {
                Log.e("BookFetcher", "Error fetching books", e);
            }
            return null;
        });
    }
}