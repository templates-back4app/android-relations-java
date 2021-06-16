package com.emre.one_to_many_java.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.author.AuthorAdapter;
import com.emre.one_to_many_java.bookList.BookListActivity;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.emre.one_to_many_java.publisher.PublisherAdapter;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText titleInput;
    private AppCompatEditText yearInput;
    private RecyclerView recyclerViewPublishers;
    private RecyclerView recyclerViewGenres;
    private RecyclerView recyclerViewAuthors;
    private Button add;
    private ImageView back;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        progressDialog = new ProgressDialog(this);
        titleInput = findViewById(R.id.titleInput);
        yearInput = findViewById(R.id.yearInput);
        recyclerViewPublishers = findViewById(R.id.recyclerViewPublishers);
        recyclerViewGenres = findViewById(R.id.recyclerViewGenres);
        recyclerViewAuthors = findViewById(R.id.recyclerViewAuthors);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        clicks();
        getPublishers();
        getAuthors();
        getGenres();
    }

    private void getPublishers() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Publisher");
        query.findInBackground((objects, e) -> {
            if (e == null) {

                progressDialog.dismiss();
                List<ParseObjectModel> list = new ArrayList<>();
                for (ParseObject parseObject : objects) {
                    list.add(new ParseObjectModel(parseObject));
                }

                BookPublisherAdapter adapter = new BookPublisherAdapter(list, this);
                recyclerViewPublishers.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewPublishers.setNestedScrollingEnabled(false);
                recyclerViewPublishers.setAdapter(adapter);
            } else {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAuthors() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Author");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                progressDialog.dismiss();
                List<ParseObjectModel> list = new ArrayList<>();
                for (ParseObject parseObject : objects) {
                    list.add(new ParseObjectModel(parseObject));
                }

                BookAuthorAdapter adapter = new BookAuthorAdapter(list, this);
                recyclerViewAuthors.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewAuthors.setNestedScrollingEnabled(false);
                recyclerViewAuthors.setAdapter(adapter);
            } else {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGenres() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Genre");
        query.findInBackground((objects, e) -> {
            if (e == null) {

                progressDialog.dismiss();
                List<ParseObjectModel> list = new ArrayList<>();
                for (ParseObject parseObject : objects) {
                    list.add(new ParseObjectModel(parseObject));
                }

                BookGenreAdapter adapter = new BookGenreAdapter(list, this);
                recyclerViewGenres.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewGenres.setNestedScrollingEnabled(false);
                recyclerViewGenres.setAdapter(adapter);
            } else {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clicks() {
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                addBook(titleInput.getText().toString(), yearInput.getText().toString());
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void addBook(String title, String year) {
        ParseObject book, genre, publisher;
        book = new ParseObject("Book");

        ParseRelation<ParseObject> relation;
        if (title == null || title.equals("")) {
            Toast.makeText(this, "Please write a title", Toast.LENGTH_SHORT).show();
            return;
        }
        if (year == null || year.equals("")) {
            Toast.makeText(this, "Please write a year", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recyclerViewGenres.getAdapter() != null) {
            genre = ((BookGenreAdapter) recyclerViewGenres.getAdapter()).getSelectedItem();
            if (genre == null) {
                Toast.makeText(this, "Please select a Genre", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recyclerViewPublishers.getAdapter() != null) {
            publisher = ((BookPublisherAdapter) recyclerViewPublishers.getAdapter()).getSelectedItem();
            if (publisher == null) {
                Toast.makeText(this, "Please select a Publisher", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (recyclerViewAuthors.getAdapter() != null) {
            relation = ((BookAuthorAdapter) recyclerViewAuthors.getAdapter()).getSelectedItems(book);
            if (relation == null) {
                Toast.makeText(this, "Please select Author/s", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        book.put("genre", genre);
        book.put("publisher", publisher);
        book.put("title", title);
        book.put("year", year);
        book.saveInBackground(e -> {
            progressDialog.hide();
            if (e == null) {
                Toast.makeText(AddBookActivity.this, "Book saved successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddBookActivity.this, BookListActivity.class));
                finish();
            } else {
                Toast.makeText(AddBookActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}