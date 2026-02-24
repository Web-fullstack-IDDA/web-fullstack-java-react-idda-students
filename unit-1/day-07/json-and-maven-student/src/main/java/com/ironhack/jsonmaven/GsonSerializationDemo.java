package com.ironhack.jsonmaven;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Demonstrates Gson serialization: converting Java objects to JSON strings using toJson().
 * Covers compact vs. pretty-printed output and nested object serialization.
 */
public class GsonSerializationDemo {

    public static void run() {

        // Part 1: Basic Serialization
        System.out.println("--- Part 1: Basic Serialization with toJson() ---");
        System.out.println();

        Gson gson = new Gson();

        Player player = new Player("Stephen Curry", 30, "Guard", true);

        String playerJson = gson.toJson(player);

        System.out.println("JSON output: " + playerJson);
        System.out.println();

        // Part 2: Pretty Printing
        System.out.println("--- Part 2: Compact vs. Pretty-Printed JSON ---");
        System.out.println();

        // GsonBuilder lets you customize output — setPrettyPrinting adds indentation
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String prettyPlayerJson = prettyGson.toJson(player);
        System.out.println("Pretty-printed:");
        System.out.println(prettyPlayerJson);
        System.out.println();

        // Part 3: Serializing Nested Objects
        System.out.println("--- Part 3: Serializing Nested Objects ---");
        System.out.println();

        Coach coach = new Coach("Erik Spoelstra", 16);

        List<Player> roster = List.of(
                new Player("Jimmy Butler", 22, "Forward", true),
                new Player("Bam Adebayo", 13, "Center", true),
                new Player("Tyler Herro", 14, "Guard", false)
        );

        Team team = new Team("Miami Heat", "Miami", 3, true, coach, roster);

        // toJson() recursively serializes nested objects and lists
        String teamJson = prettyGson.toJson(team);

        System.out.println(teamJson);
    }
}
