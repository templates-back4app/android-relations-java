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

public class BookPublisherAdapter extends RecyclerView.Adapter<BookPublisherHolder> {

    private List<ParseObjectModel> list;
    private Context context;
    private int lastSelectedPosition = -1;


    public BookPublisherAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ParseObject getSelectedItem() {
        if (lastSelectedPosition == -1) {
            return null;
        } else {
            return list.get(lastSelectedPosition).getObject();
        }
    }
    @NonNull
    @Override
    public BookPublisherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_with_checkbox, parent, false);
        return new BookPublisherHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookPublisherHolder holder, int position) {
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

class BookPublisherHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;

    public BookPublisherHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}
