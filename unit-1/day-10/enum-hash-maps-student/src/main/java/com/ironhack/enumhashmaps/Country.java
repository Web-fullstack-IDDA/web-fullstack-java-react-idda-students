package com.ironhack.enumhashmaps;

// A Country model class used to demonstrate HashMap with objects as values.
public class Country {

    private String name;
    private String capital;
    private long population;

    public Country(String name, String capital, long population) {
        this.name = name;
        this.capital = capital;
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public long getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return name + " (capital: " + capital + ", population: " + population + ")";
    }
}
