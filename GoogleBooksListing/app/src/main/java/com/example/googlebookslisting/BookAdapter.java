package com.example.googlebookslisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//Brenna Pavlinchak
//C202501
//BookAdapter.java

public class BookAdapter extends android.widget.BaseAdapter
{
    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books)
    {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount()
    {
        return books.size();
    }

    @Override
    public Object getItem(int position)
    {
        return books.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_book, parent, false);
        }
        Book book = books.get(position);

        TextView titleTextView = convertView.findViewById(R.id.bookTitle);
        titleTextView.setText(book.getTitle());

        ImageView thumbnailImageView = convertView.findViewById(R.id.bookThumbnail);

        if (book.getThumbnailUrl() != null)
        {
            Picasso.get().load(book.getThumbnailUrl()).into(thumbnailImageView);
        }
        else
        {
            thumbnailImageView.setImageResource(R.drawable.placeholder_image);
        }
        return convertView;
    }
}