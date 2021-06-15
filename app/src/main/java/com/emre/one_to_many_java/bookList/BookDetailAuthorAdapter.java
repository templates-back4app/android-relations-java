package com.emre.one_to_many_java.bookList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class BookDetailAuthorAdapter extends RecyclerView.Adapter<BookDetailAuthorHolder> {

    List<ParseObject> list;
    Context context;


    public BookDetailAuthorAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookDetailAuthorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_card, parent, false);

        return new BookDetailAuthorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDetailAuthorHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.text.setText(object.getString("name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookDetailAuthorHolder extends RecyclerView.ViewHolder {

    TextView text;

    public BookDetailAuthorHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.text);
    }
}
