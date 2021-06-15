package com.emre.one_to_many_java.publisher;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.one_to_many_java.R;
import com.emre.one_to_many_java.models.ParseObjectModel;

import java.util.ArrayList;
import java.util.List;

public class PublisherAdapter extends RecyclerView.Adapter<PublisherHolder> {

    private List<ParseObjectModel> list = new ArrayList<>();
    private Context context;

    public PublisherAdapter(List<ParseObjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PublisherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_card, parent, false);
        return new PublisherHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PublisherHolder holder, int position) {
        ParseObjectModel object = list.get(position);
        holder.text.setText(object.getObject().getString("name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PublisherHolder extends RecyclerView.ViewHolder {
    TextView text;

    public PublisherHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.text);
    }
}
