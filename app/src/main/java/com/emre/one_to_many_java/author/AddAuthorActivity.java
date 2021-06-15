package com.emre.one_to_many_java.author;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.genre.GenreAdapter;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.emre.one_to_many_java.publisher.PublisherAdapter;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddAuthorActivity extends AppCompatActivity implements View.OnClickListener {


    private AppCompatEditText inputAuthor;
    private Button add;
    private ImageView back;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        inputAuthor = findViewById(R.id.inputAuthor);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
        clicks();
        getAuthors();
    }

    private void clicks() {
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (inputAuthor.getText().toString().length() != 0)
                    addAuthor(inputAuthor.getText().toString());
                else
                    Toast.makeText(this, "Please write an Author", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getAuthors() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Author");
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            List<ParseObjectModel> list = new ArrayList<>();
            for (ParseObject parseObject : objects) {
                list.add(new ParseObjectModel(parseObject));
            }

            AuthorAdapter adapter = new AuthorAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }

    private void addAuthor(String name){
        progressDialog.show();
        ParseObject parseObject = new ParseObject("Author");
        parseObject.put("name", name);
        parseObject.saveInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                getAuthors();
                inputAuthor.setText("");
                Toast.makeText(this, "Author saved successfully", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}