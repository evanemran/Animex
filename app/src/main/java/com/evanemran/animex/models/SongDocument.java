package com.evanemran.animex.models;

import java.io.Serializable;

public class SongDocument implements Serializable {
    public int anime_id;
    public String title;
    public String artist;
    public String album;
    public int year;
    public int season;
    public int duration;
    public String preview_url;
    public String open_spotify_url;
    public String local_spotify_url;
    public int type;
    public int id;
}
