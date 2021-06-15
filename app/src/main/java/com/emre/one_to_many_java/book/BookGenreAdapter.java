package com.emre.one_to_many_java.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;
import com.parse.ParseObject;

import java.util.List;

public class BookGenreAdapter extends RecyclerView.Adapter<BookGenreHolder> {
    private List<ParseObjectModel> list;
    private Context context;
    private int lastSelectedPosition = -1;


    public BookGenreAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public BookGenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_with_checkbox, parent, false);
        return new BookGenreHolder(v);
    }

    public ParseObject getSelectedItem() {
        if (lastSelectedPosition == -1) {
            return null;
        } else {
            return list.get(lastSelectedPosition).getObject();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BookGenreHolder holder, int position) {
        ParseObjectModel model = list.get(position);
        holder.checkBox.setText(model.getObject().getString("name"));

        if (model.isChecked())
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        holder.checkBox.setOnClickListener(v -> {
            if (lastSelectedPosition == -1) {//İlk kez tıklandı
                lastSelectedPosition = position;
                model.setChecked(true);
            } else {
                list.get(lastSelectedPosition).setChecked(false);
                lastSelectedPosition = position;
                model.setChecked(true);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookGenreHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;

    public BookGenreHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}