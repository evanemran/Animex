package com.evanemran.animex.enums;

public enum Status {
    FINISHED(0, "Finished"),
    RELEASING(0, "Releasing"),
    NOT_YET_RELEASED(0, "Upcoming"),
    CANCELLED(0, "Cancelled");

    private int id = 0;
    private String name = "";

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
