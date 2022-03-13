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
import com.evanemran.animex.models.EpisodeDocument;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodesListAdapter extends RecyclerView.Adapter<EpisodesListViewHolder>{

    Context context;
    List<EpisodeDocument> list;
    ClickListener listener;

    public EpisodesListAdapter(Context context, List<EpisodeDocument> list, ClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EpisodesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodesListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_episodes, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesListViewHolder holder, int position) {
        holder.textView_episodes_name.setText(list.get(position).title);

        holder.textView_episodes_name.setSelected(true);

        holder.cardView_episodes.setOnClickListener(new View.OnClickListener() {
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

class EpisodesListViewHolder extends RecyclerView.ViewHolder {

    CardView cardView_episodes;
    TextView textView_episodes_name;


    public EpisodesListViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView_episodes = itemView.findViewById(R.id.cardView_episodes);
        textView_episodes_name = itemView.findViewById(R.id.textView_episodes_name);
    }
}
