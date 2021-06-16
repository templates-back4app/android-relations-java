package com.emre.one_to_many_java.bookList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListHolder> {
    private List<ParseObject> list;
    private Context context;


    public BookListAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_list_layout, parent, false);
        return new BookListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.title.setText(object.getString("name"));
        ParseQuery<ParseObject> query = new ParseQuery<>("Book");
        query.whereEqualTo("publisher", object);
        query.findInBackground((objects, e) -> {
            if (e == null) {
                BooksAdapter adapter = new BooksAdapter(objects, context);
                holder.books.setLayoutManager(new LinearLayoutManager(context));
                holder.books.setAdapter(adapter);
            } else {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookListHolder extends RecyclerView.ViewHolder {
    TextView title;
    RecyclerView books;

    public BookListHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        books = itemView.findViewById(R.id.books);
    }
}

class BooksAdapter extends RecyclerView.Adapter<BooksHolder> {

    private List<ParseObject> list;
    private Context context;

    public BooksAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public BooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_book, parent, false);
        return new BooksHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.bookName.setText(object.getString("title"));
        holder.bookName.setOnClickListener(view -> {
            context.startActivity(new Intent(context, BookDetailActivity.class).putExtra("objectId", object.getObjectId()));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BooksHolder extends RecyclerView.ViewHolder {

    TextView bookName;

    public BooksHolder(@NonNull View itemView) {
        super(itemView);
        bookName = itemView.findViewById(R.id.bookName);
    }
}