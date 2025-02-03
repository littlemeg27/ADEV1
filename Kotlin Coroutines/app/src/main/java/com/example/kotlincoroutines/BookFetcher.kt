package com.example.kotlincoroutines


import android.util.Log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//Brenna Pavlinchak
//C202501
//BookFetcher.kt

object BookFetcher
{
    @JvmStatic
    suspend fun fetchBooks(apiUrl: String): List<Book> = withContext(Dispatchers.IO)
    {
        val books = mutableListOf<Book>()
        var connection: HttpURLConnection? = null

        try
        {
            val url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = reader.readText()
            reader.close()

            val root = JSONObject(response)
            val items = root.optJSONArray("items")

            if (items != null)
            {
                for (i in 0 until items.length())
                {
                    val bookObj = items.getJSONObject(i)
                    val volumeInfo = bookObj.optJSONObject("volumeInfo")

                    val title = volumeInfo?.optString("title", "Unknown Title") ?: "Unknown Title"
                    val imageUrl = volumeInfo?.optJSONObject("imageLinks")?.optString("thumbnail", "")

                    books.add(Book(title, imageUrl ?: ""))
                }
            }
        }
        catch (e: Exception)
        {
            Log.e("BookFetcher", "Error fetching books", e)
        }
        finally
        {
            connection?.disconnect()
        }
        books
    }
}