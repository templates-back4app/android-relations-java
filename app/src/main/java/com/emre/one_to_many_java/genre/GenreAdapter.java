package com.emre.one_to_many_java.genre;

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

public class GenreAdapter extends RecyclerView.Adapter<GenreHolder> {

    List<ParseObjectModel> list;
    Context context;

    public GenreAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_card, parent, false);
        return new GenreHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        ParseObjectModel name = list.get(position);
        holder.textView.setText(name.getObject().getString("name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class GenreHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public GenreHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
    }
}
