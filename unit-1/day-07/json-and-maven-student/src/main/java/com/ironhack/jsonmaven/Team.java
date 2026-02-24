package com.ironhack.jsonmaven;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class representing a sports team.
 * Demonstrates nested objects (Coach) and arrays (List of Player) in JSON-to-Java mapping.
 */
public class Team {

    @SerializedName("team_name")
    private String teamName;

    private String city;
    private int championships;
    private boolean active;

    @SerializedName("head_coach")
    private Coach headCoach;

    private List<Player> roster;

    public Team(String teamName, String city, int championships, boolean active, Coach headCoach, List<Player> roster) {
        this.teamName = teamName;
        this.city = city;
        this.championships = championships;
        this.active = active;
        this.headCoach = headCoach;
        this.roster = roster;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCity() {
        return city;
    }

    public int getChampionships() {
        return championships;
    }

    public boolean isActive() {
        return active;
    }

    public Coach getHeadCoach() {
        return headCoach;
    }

    public List<Player> getRoster() {
        return roster;
    }
}
