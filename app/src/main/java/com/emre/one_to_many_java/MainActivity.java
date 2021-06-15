package com.emre.one_to_many_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emre.one_to_many_java.author.AddAuthorActivity;
import com.emre.one_to_many_java.book.AddBookActivity;
import com.emre.one_to_many_java.bookList.BookListActivity;
import com.emre.one_to_many_java.genre.AddGenreActivity;
import com.emre.one_to_many_java.publisher.AddPublisherActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addGenre;
    private Button addPublisher;
    private Button addAuthor;
    private Button addBook;
    private Button listPublisherBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addGenre = findViewById(R.id.addGenre);
        addPublisher = findViewById(R.id.addPublisher);
        addAuthor = findViewById(R.id.addAuthor);
        addBook = findViewById(R.id.addBook);
        listPublisherBooks = findViewById(R.id.listPublisherBooks);
        clicks();
    }

    private void clicks() {
        addGenre.setOnClickListener(this);
        addPublisher.setOnClickListener(this);
        addAuthor.setOnClickListener(this);
        addBook.setOnClickListener(this);
        listPublisherBooks.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addGenre:
                startActivity(new Intent(this, AddGenreActivity.class));
                break;
            case R.id.addPublisher:
                startActivity(new Intent(this, AddPublisherActivity.class));
                break;
            case R.id.addAuthor:
                startActivity(new Intent(this, AddAuthorActivity.class));
                break;
            case R.id.addBook:
                startActivity(new Intent(this, AddBookActivity.class));
                break;
            case R.id.listPublisherBooks:
                startActivity(new Intent(this, BookListActivity.class));
                break;
        }
    }
}