package com.evanemran.animex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evanemran.animex.adapters.EpisodesListAdapter;
import com.evanemran.animex.enums.Formats;
import com.evanemran.animex.enums.Status;
import com.evanemran.animex.listeners.ClickListener;
import com.evanemran.animex.listeners.ResponseListener;
import com.evanemran.animex.manager.RequestManager;
import com.evanemran.animex.models.Document;
import com.evanemran.animex.models.EpisodeDocument;
import com.evanemran.animex.models.EpisodesResponse;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    Document document;
    ImageView imageView_banner, imageView_main;
    TextView textView_seasons, textView_year, textView_episodes, textView_episodes_duration, textView_score, textView_title;
    TextView textView_start_date, textView_end_date, textView_genre_container, textView_genres, textView_details, textView_ed, textView_sd;
    LinearLayout youtube_viewer;
    RecyclerView recycler_episodes;
    EpisodesListAdapter episodesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        document = (Document) getIntent().getSerializableExtra("data");

        findViews();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        Picasso.get().load(document.banner_image).into(imageView_banner);
        Picasso.get().load(document.cover_image).into(imageView_main);
        imageView_banner.setColorFilter(ContextCompat.getColor(this, R.color.sand), PorterDuff.Mode.MULTIPLY);

        textView_seasons.setText("Seasons: "+document.season_period);
        textView_year.setText("Year: "+document.season_year);
        textView_episodes.setText("Episodes: "+document.episodes_count);
        textView_episodes_duration.setText("Episode Duration: "+document.episode_duration+" Min");
        textView_score.setText("Score: "+document.score);
//        Formats format = (Formats) document.format;
        textView_start_date.setText("Format: "+ Formats.values()[document.format]);
        textView_end_date.setText("Status: "+ Status.values()[document.status]);
//        textView_genre_container.setText("Score: "+document.genres);
        textView_details.setText(document.descriptions.en);
        textView_title.setText(document.titles.en);
        textView_title.setSelected(true);

        //get episodes
        RequestManager manager = new RequestManager(this);
        manager.getEpisodes(episodesResponseResponseListener, document.anilist_id);

        StringBuilder genres = new StringBuilder();
        genres.append(document.genres);
        textView_genres.setText(genres);
        textView_genres.setSelected(true);

        SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat postFormat = new SimpleDateFormat("MMM dd yyyy");
        try {
            Date startDate = preFormat.parse(document.start_date);
            Date endDate = preFormat.parse(document.end_date);
            textView_sd.setText(postFormat.format(startDate));
            textView_ed.setText(postFormat.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (document.trailer_url==null){
            youtube_viewer.setVisibility(View.GONE);
        }
        else{
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String video = document.trailer_url.substring(document.trailer_url.lastIndexOf("/")+1);
                    String videoId = video;
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }

    }

    private void findViews() {
        imageView_banner = findViewById(R.id.imageView_banner);
        imageView_main = findViewById(R.id.imageView_main);

        textView_seasons = findViewById(R.id.textView_seasons);
        textView_year = findViewById(R.id.textView_year);
        textView_episodes = findViewById(R.id.textView_episodes);
        textView_episodes_duration = findViewById(R.id.textView_episodes_duration);
        textView_score = findViewById(R.id.textView_score);
        textView_start_date = findViewById(R.id.textView_start_date);
        textView_end_date = findViewById(R.id.textView_end_date);
        textView_genre_container = findViewById(R.id.textView_genre_container);
        textView_genres = findViewById(R.id.textView_genres);
        textView_details = findViewById(R.id.textView_details);
        textView_title = findViewById(R.id.textView_title);
        textView_ed = findViewById(R.id.textView_ed);
        textView_sd = findViewById(R.id.textView_sd);
        youtube_viewer = findViewById(R.id.youtube_viewer);
        recycler_episodes = findViewById(R.id.recycler_episodes);
    }

    private final ResponseListener<EpisodesResponse> episodesResponseResponseListener = new ResponseListener<EpisodesResponse>() {
        @Override
        public void didFetch(EpisodesResponse response, String message) {
            recycler_episodes.setHasFixedSize(true);
            recycler_episodes.setLayoutManager(new GridLayoutManager(DetailsActivity.this, 2));
            episodesListAdapter = new EpisodesListAdapter(DetailsActivity.this, response.documents, clickListener);
            recycler_episodes.setAdapter(episodesListAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final ClickListener<EpisodeDocument> clickListener = new ClickListener<EpisodeDocument>() {
        @Override
        public void onClicked(EpisodeDocument object) {
            startActivity(new Intent(DetailsActivity.this, VideoActivity.class)
            .putExtra("video", object.video));
        }
    };
}