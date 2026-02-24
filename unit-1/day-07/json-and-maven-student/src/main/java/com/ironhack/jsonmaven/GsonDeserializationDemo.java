package com.ironhack.jsonmaven;

import com.google.gson.Gson;

import java.util.List;

/**
 * Demonstrates Gson deserialization: converting JSON strings to Java objects using fromJson().
 * Covers nested object deserialization, field name mismatches, and round-trip verification.
 */
public class GsonDeserializationDemo {

    public static void run() {

        // Part 1: Basic Deserialization
        System.out.println("--- Part 1: Basic Deserialization with fromJson() ---");
        System.out.println();

        Gson gson = new Gson();

        String playerJson = """
                {
                  "name": "LeBron James",
                  "jersey_number": 23,
                  "position": "Forward",
                  "starter": true
                }
                """;

        System.out.println("Input JSON:");
        System.out.println(playerJson);

        // fromJson(jsonString, TargetClass.class) parses JSON into a Java object
        Player player = gson.fromJson(playerJson, Player.class);

        System.out.println("player.getName()         = " + player.getName());
        System.out.println("player.getJerseyNumber() = " + player.getJerseyNumber());
        System.out.println("player.getPosition()     = " + player.getPosition());
        System.out.println("player.isStarter()       = " + player.isStarter());
        System.out.println();

        // Part 2: Deserializing Nested Objects -- full example (serialize and deserialize)
        System.out.println("--- Part 2: Deserializing Nested Objects ---");
        System.out.println();

        Coach coach = new Coach("JJ Redick", 1);

        List<Player> roster = List.of(
                new Player("Lebron James", 23, "Forward", true),
                new Player("Anthony Davis", 3, "Center", true),
                new Player("Austin Reaves", 15, "Guard", false)
        );

        Team teamToJson = new Team("Los Angeles Lakers", "Los Angeles", 17, true, coach, roster);

        String teamJson = gson.toJson(teamToJson);

        System.out.println(teamJson);

        // One call to fromJson() deserializes the entire object graph
        Team team = gson.fromJson(teamJson, Team.class);

        System.out.println("Deserialized Team:");
        System.out.println("  Team name:     " + team.getTeamName());
        System.out.println("  City:          " + team.getCity());
        System.out.println("  Championships: " + team.getChampionships());
        System.out.println("  Active:        " + team.isActive());
        System.out.println();

        System.out.println("  Head Coach:    " + team.getHeadCoach().getName());
        System.out.println("  Experience:    " + team.getHeadCoach().getYearsExperience() + " years");
        System.out.println();

        System.out.println("  Roster (" + team.getRoster().size() + " players):");
        for (Player p : team.getRoster()) {
            String starterStatus = p.isStarter() ? "starter" : "bench";
            System.out.println("    #" + p.getJerseyNumber() + " " + p.getName()
                    + " (" + p.getPosition() + ", " + starterStatus + ")");
        }
        System.out.println();

        // Part 3: What Happens with Mismatched Names
        System.out.println("--- Part 3: Mismatched Field Names ---");
        System.out.println();

        System.out.println("Without @SerializedName, these JSON keys would NOT match:");
        System.out.println("  JSON 'jersey_number'    vs Java 'jerseyNumber'    -> field = 0");
        System.out.println("  JSON 'team_name'        vs Java 'teamName'        -> field = null");
        System.out.println("  JSON 'head_coach'       vs Java 'headCoach'       -> field = null");
        System.out.println("  JSON 'years_experience' vs Java 'yearsExperience' -> field = 0");
        System.out.println();
        System.out.println("With @SerializedName, Gson knows exactly which JSON key maps to which field.");
        System.out.println();
    }
}
