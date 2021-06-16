package com.emre.one_to_many_java.genre;

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
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AddGenreActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText inputGenre;
    private Button add;
    private ImageView back;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genre);
        inputGenre = findViewById(R.id.inputGenre);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
        clicks();
        getGenres();
    }

    private void clicks() {
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (inputGenre.getText().toString().length() != 0)
                    addGenre(inputGenre.getText().toString());
                else
                    Toast.makeText(this, "Please write a genre", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    private void getGenres() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Genre");
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            List<ParseObjectModel> list = new ArrayList<>();
            for (ParseObject parseObject : objects) {
                list.add(new ParseObjectModel(parseObject));
            }

            GenreAdapter adapter = new GenreAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }

    private void addGenre(String name) {
        //We are taking this name parameter from the input.
        progressDialog.show();
        ParseObject parseObject = new ParseObject("Genre");
        parseObject.put("name", name);
        parseObject.saveInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                getGenres();
                inputGenre.setText("");
                Toast.makeText(this, "Genre saved successfully", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}