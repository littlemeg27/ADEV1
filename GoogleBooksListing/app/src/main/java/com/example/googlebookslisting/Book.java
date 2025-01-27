package com.example.googlebookslisting;

//Brenna Pavlinchak
//C202501
//Book.java

public class Book
{
    private String title;
    private String thumbnailUrl;

    public Book(String title, String thumbnailUrl)
    {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }
}
