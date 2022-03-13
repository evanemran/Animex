package com.evanemran.animex;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);

        try
        {
            Toast.makeText(VideoActivity.this, "Playing Video", Toast.LENGTH_SHORT).show();
            String path = (String) getIntent().getStringExtra("video");
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
            videoView.start();
        } catch (Exception e)
        {
            Toast.makeText(VideoActivity.this, "Couldn't Play Video!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        }
    }
}