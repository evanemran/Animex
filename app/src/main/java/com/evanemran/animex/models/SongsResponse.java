package com.evanemran.animex.models;

import java.io.Serializable;
import java.util.List;

public class SongsResponse implements Serializable {
    public int current_page;
    public int count;
    public List<SongDocument> documents;
    public int last_page;
}
