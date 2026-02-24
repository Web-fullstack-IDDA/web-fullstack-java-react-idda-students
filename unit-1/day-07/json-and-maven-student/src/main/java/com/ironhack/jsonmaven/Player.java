package com.ironhack.jsonmaven;

import com.google.gson.annotations.SerializedName;

/**
 * Model class representing a sports player.
 * Demonstrates JSON key-to-Java field mapping and @SerializedName for snake_case keys.
 */
public class Player {

    private String name;

    @SerializedName("jersey_number")
    private int jerseyNumber;

    private String position;

    private boolean starter;

    public Player(String name, int jerseyNumber, String position, boolean starter) {
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.starter = starter;
    }

    public String getName() {
        return name;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public String getPosition() {
        return position;
    }

    public boolean isStarter() {
        return starter;
    }
}
