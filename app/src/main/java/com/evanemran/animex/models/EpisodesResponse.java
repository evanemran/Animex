package com.evanemran.animex.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EpisodesResponse implements Serializable {
    public int current_page;
    public int count;
    public ArrayList<EpisodeDocument> documents;
    public int last_page;
}
