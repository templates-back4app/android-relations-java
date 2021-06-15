package com.emre.one_to_many_java.author;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorHolder> {
    private List<ParseObjectModel> list;
    private Context context;


    public AuthorAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_card, parent, false);
        return new AuthorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorHolder holder, int position) {
        ParseObjectModel object = list.get(position);
        holder.text.setText(object.getObject().getString("name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AuthorHolder extends RecyclerView.ViewHolder {
    TextView text;

    public AuthorHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.text);
    }
}