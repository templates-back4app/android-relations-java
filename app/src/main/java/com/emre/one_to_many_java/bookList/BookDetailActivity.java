package com.emre.one_to_many_java.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emre.one_to_many_java.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private TextView bookTitle;
    private TextView bookYear;
    private TextView bookGenre;
    private TextView bookPublisher;
    private RecyclerView authorRecyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        back = findViewById(R.id.back);
        bookPublisher = findViewById(R.id.bookPublisher);
        bookGenre = findViewById(R.id.bookGenre);
        progressDialog = new ProgressDialog(this);
        bookYear = findViewById(R.id.bookYear);
        bookTitle = findViewById(R.id.bookTitle);
        authorRecyclerView = findViewById(R.id.authorRecyclerView);
        clicks();
        getBookWithDetails();
    }

    private void clicks() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void getBookWithDetails() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Book");
        query.getInBackground(getIntent().getStringExtra("objectId"), (object, e) -> {
            if (e == null) {
                bookTitle.setText("Title: " +object.getString("title"));
                bookYear.setText("Year: " +object.getString("year"));
                try {
                    bookGenre.setText("Genre: " +object.getParseObject("genre").fetchIfNeeded().getString("name"));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                try {
                    bookPublisher.setText("Publisher: " + object.getParseObject("publisher").fetchIfNeeded().getString("name"));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                object.getRelation("author_relation").getQuery().findInBackground((objects, e1) -> {
                    progressDialog.hide();
                    if (e1 == null) {
                        BookDetailAuthorAdapter adapter = new BookDetailAuthorAdapter(objects, this);
                        authorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                        authorRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                progressDialog.hide();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}