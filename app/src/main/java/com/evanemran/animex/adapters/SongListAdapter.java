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
import com.evanemran.animex.models.SongDocument;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListViewHolder>{

    Context context;
    List<SongDocument> list;
    ClickListener listener;

    public SongListAdapter(Context context, List<SongDocument> list, ClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_songs, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SongListViewHolder holder, int position) {
        holder.textView_song_name.setText(list.get(position).title);
        holder.textView_song_artist.setText("Artist: " + list.get(position).artist);
        holder.textView_song_album.setText("Album: " + list.get(position).album);
        holder.textView_song_year.setText("Year: " + list.get(position).year);
        holder.textView_song_duration.setText("Duration: " + createTime(list.get(position).duration));

        holder.textView_song_name.setSelected(true);
        holder.textView_song_artist.setSelected(true);
        holder.textView_song_album.setSelected(true);
        holder.textView_song_year.setSelected(true);
        holder.textView_song_duration.setSelected(true);

        holder.cardView_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    public String createTime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;
        time+= min + ":";

        if (sec<10)
        {
            time+= "0";
        }
        time+=sec;

        return time;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SongListViewHolder extends RecyclerView.ViewHolder {

    CardView cardView_song;
    TextView textView_song_name, textView_song_artist, textView_song_album, textView_song_year, textView_song_duration;


    public SongListViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView_song = itemView.findViewById(R.id.cardView_song);
        textView_song_name = itemView.findViewById(R.id.textView_song_name);
        textView_song_artist = itemView.findViewById(R.id.textView_song_artist);
        textView_song_album = itemView.findViewById(R.id.textView_song_album);
        textView_song_year = itemView.findViewById(R.id.textView_song_year);
        textView_song_duration = itemView.findViewById(R.id.textView_song_duration);
    }
}
