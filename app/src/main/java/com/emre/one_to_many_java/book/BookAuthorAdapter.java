package com.emre.one_to_many_java.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class BookAuthorAdapter extends RecyclerView.Adapter<BookAuthorHolder> {

    private List<ParseObjectModel> list;
    private Context context;


    public BookAuthorAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAuthorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_with_checkbox, parent, false);
        return new BookAuthorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAuthorHolder holder, int position) {
        ParseObjectModel model = list.get(position);
        holder.checkBox.setText(model.getObject().getString("name"));

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            model.setChecked(b);
        });
    }

    public ParseRelation<ParseObject> getSelectedItems(ParseObject parseObject) {
        ParseRelation<ParseObject> relation = parseObject.getRelation("author_relation");

        for (ParseObjectModel object : this.list) {
            if (object.isChecked())
                relation.add(object.getObject());
        }
        return relation;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookAuthorHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;

    public BookAuthorHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}
