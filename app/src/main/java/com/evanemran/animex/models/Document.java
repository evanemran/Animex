package com.evanemran.animex.models;

import com.evanemran.animex.enums.Formats;

import java.io.Serializable;
import java.util.List;

public class Document implements Serializable {
    public int anilist_id;
    public int mal_id;
    public int tmdb_id;
    public int format;
    public int status;
    public Titles titles;
    public Descriptions descriptions;
    public String start_date;
    public String end_date;
    public int season_period;
    public int season_year;
    public int episodes_count;
    public int episode_duration;
    public String trailer_url;
    public String cover_image;
    public String cover_color;
    public String banner_image;
    public List<String> genres;
    public int score;
    public boolean nsfw;
    public boolean hasCoverImage;
    public int id;
    public int prequel;
    public int sequel;
    public int weekly_airing_day;
    public List<Saga> sagas;
}
