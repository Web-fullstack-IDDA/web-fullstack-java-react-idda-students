package com.ironhack.jsonmaven;

import com.google.gson.annotations.SerializedName;

/**
 * Model class representing a team's head coach.
 * Used as a nested object inside Team to demonstrate JSON object nesting.
 */
public class Coach {

    private String name;

    @SerializedName("years_experience")
    private int yearsExperience;

    public Coach(String name, int yearsExperience) {
        this.name = name;
        this.yearsExperience = yearsExperience;
    }

    public String getName() {
        return name;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }
}
