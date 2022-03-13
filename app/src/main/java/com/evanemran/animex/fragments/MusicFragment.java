package com.evanemran.animex.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.animex.R;
import com.evanemran.animex.adapters.AnimeListAdapter;
import com.evanemran.animex.adapters.SongListAdapter;
import com.evanemran.animex.listeners.ClickListener;
import com.evanemran.animex.listeners.ResponseListener;
import com.evanemran.animex.manager.RequestManager;
import com.evanemran.animex.models.AnimeResponse;
import com.evanemran.animex.models.Document;
import com.evanemran.animex.models.SongDocument;
import com.evanemran.animex.models.SongsResponse;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.io.IOException;

public class MusicFragment extends Fragment {
    View view;
    RecyclerView recycler_song;
    RequestManager manager;
    ProgressBar progressBar;
    SongListAdapter songListAdapter;
    MediaPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_songs, container, false);
        recycler_song = view.findViewById(R.id.recycler_song);

        progressBar = (ProgressBar) view.findViewById(R.id.loader);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        manager = new RequestManager(getContext());

        manager.getSongs(animeResponseResponseListener);
        return view;
    }

    private final ResponseListener<SongsResponse> animeResponseResponseListener = new ResponseListener<SongsResponse>() {
        @Override
        public void didFetch(SongsResponse response, String message) {
            recycler_song.setHasFixedSize(true);
            recycler_song.setLayoutManager(new GridLayoutManager(getContext(), 1));
            songListAdapter = new SongListAdapter(getContext(), response.documents, clickListener);
            recycler_song.setAdapter(songListAdapter);
            progressBar.setVisibility(View.GONE);
            recycler_song.setVisibility(View.VISIBLE);
        }

        @Override
        public void didError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final ClickListener<SongDocument> clickListener = new ClickListener<SongDocument>() {
        @Override
        public void onClicked(SongDocument object) {
            showPopup(object);
        }
    };

    private void showPopup(SongDocument object) {
        final Dialog popupDialog = new Dialog(getContext());
        popupDialog.setContentView(R.layout.popup_song);
        popupDialog.setCanceledOnTouchOutside(false);
        popupDialog.setTitle(object.title);

        TextView textView_song_name = (TextView) popupDialog.findViewById(R.id.textView_song_name);
        textView_song_name.setText(object.title);
        textView_song_name.setSelected(true);
        LinearLayout play_preview = (LinearLayout) popupDialog.findViewById(R.id.play_preview);
        play_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = new MediaPlayer();
                try {
                    Toast.makeText(getContext(), "Playing Preview", Toast.LENGTH_SHORT).show();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(object.preview_url);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Couldn't play audio!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayout play_spotify = (LinearLayout) popupDialog.findViewById(R.id.play_spotify);
        play_spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.open_spotify_url));
                startActivity(browserIntent);
            }
        });
        Button button_cancel= (Button) popupDialog.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player!=null)
                {
                    try {
                        player.stop();
                        player.release();
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }
                }
                popupDialog.dismiss();
            }
        });

        popupDialog.setCanceledOnTouchOutside(false);
        popupDialog.show();

        Window window = popupDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
