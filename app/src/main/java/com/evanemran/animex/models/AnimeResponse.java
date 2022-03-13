package com.evanemran.animex.models;

import java.io.Serializable;
import java.util.List;

public class AnimeResponse implements Serializable {
    public int current_page;
    public int count;
    public List<Document> documents;
    public int last_page;
}
