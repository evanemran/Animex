package com.evanemran.animex.enums;

public enum Formats {
    TV(0, "Tv"),
    TV_SHORT(1, "Tv Short"),
    MOVIE(2, "Movie"),
    SPECIAL(3, "Special"),
    OVA(4, "OVA"),
    ONA(5, "ONA"),
    MUSIC(6, "Music");


    private int id = 0;
    private String name = "";

    Formats(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
