package com.emre.one_to_many_java.publisher;

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
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddPublisherActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText inputPublisher;
    private Button add;
    private ImageView back;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publisher);
        inputPublisher = findViewById(R.id.inputPublisher);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
        clicks();
        getPublishers();
    }

    private void clicks() {
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:

                if (inputPublisher.getText().toString().length() != 0)
                    addPublisher(inputPublisher.getText().toString());
                else
                    Toast.makeText(this, "Please write a Publisher", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getPublishers() {
        progressDialog.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Publisher");
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            List<ParseObjectModel> list = new ArrayList<>();
            for (ParseObject parseObject : objects) {
                list.add(new ParseObjectModel(parseObject));
            }

            PublisherAdapter adapter = new PublisherAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }


    private void addPublisher(String name) {
        //We are taking this name parameter from the input.
        progressDialog.show();
        ParseObject parseObject = new ParseObject("Publisher");
        parseObject.put("name", name);
        parseObject.saveInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                getPublishers();
                inputPublisher.setText("");
                Toast.makeText(this, "Publisher saved successfully", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}