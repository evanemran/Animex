package com.evanemran.animex.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.animex.R;
import com.evanemran.animex.listeners.ClickListener;
import com.evanemran.animex.models.Document;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListViewHolder>{

    Context context;
    List<Document> list;
    ClickListener listener;

    public AnimeListAdapter(Context context, List<Document> list, ClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimeListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_anime_home, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListViewHolder holder, int position) {
        Picasso.get().load(list.get(position).cover_image).into(holder.imageView_home);
        holder.textView_home.setText(list.get(position).titles.en);

        holder.textView_home.setSelected(true);

        holder.cardView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AnimeListViewHolder extends RecyclerView.ViewHolder {

    CardView cardView_home;
    ImageView imageView_home;
    TextView textView_home;


    public AnimeListViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView_home = itemView.findViewById(R.id.cardView_home);
        imageView_home = itemView.findViewById(R.id.imageView_home);
        textView_home = itemView.findViewById(R.id.textView_home);
    }
}
