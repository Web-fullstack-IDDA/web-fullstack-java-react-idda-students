package com.ironhack.jsonmaven;

/**
 * Demonstrates JSON structure: data types, nested objects, and arrays.
 * Uses manual JSON strings to understand the format before using Gson.
 */
public class JsonStructureDemo {

    public static void run() {

        // Part 1: The 6 JSON Data Types
        System.out.println("--- Part 1: The 6 JSON Data Types ---");
        System.out.println();

        String jsonAllTypes = """
                {
                  "team_name": "Golden State Warriors",
                  "championships": 7,
                  "win_percentage": 0.634,
                  "active": true,
                  "mascot": null,
                  "arena": {
                    "name": "Chase Center",
                    "capacity": 18064
                  },
                  "star_players": ["Stephen Curry", "Klay Thompson", "Draymond Green"]
                }
                """;

        System.out.println("JSON with all 6 data types:");
        System.out.println(jsonAllTypes);
        System.out.println("Type breakdown:");
        System.out.println("  \"team_name\"      -> STRING   (text in double quotes)");
        System.out.println("  \"championships\"  -> NUMBER   (integer, no quotes)");
        System.out.println("  \"win_percentage\" -> NUMBER   (decimal, no quotes)");
        System.out.println("  \"active\"         -> BOOLEAN  (true or false, no quotes)");
        System.out.println("  \"mascot\"         -> NULL     (null, no quotes — means 'no value')");
        System.out.println("  \"arena\"          -> OBJECT   (another { } with key-value pairs)");
        System.out.println("  \"star_players\"   -> ARRAY    (a list inside [ ])");
        System.out.println();

        // Part 2: JSON -> Java Mapping Rules
        System.out.println("--- Part 2: JSON -> Java Mapping Rules ---");
        System.out.println();

        System.out.println("Mapping Rules:");
        System.out.println("  JSON string       -> Java String");
        System.out.println("  JSON number (int)  -> Java int / Integer");
        System.out.println("  JSON number (dec)  -> Java double / Double");
        System.out.println("  JSON boolean       -> Java boolean / Boolean");
        System.out.println("  JSON null          -> Java null");
        System.out.println("  JSON object { }    -> A separate Java class");
        System.out.println("  JSON array [ ]     -> Java List<T>");
        System.out.println();

        // Part 3: Nested Objects
        System.out.println("--- Part 3: Nested Objects ---");
        System.out.println();

        String nestedJson = """
                {
                  "team_name": "Miami Heat",
                  "city": "Miami",
                  "head_coach": {
                    "name": "Erik Spoelstra",
                    "years_experience": 16
                  }
                }
                """;

        System.out.println("Nested object example:");
        System.out.println(nestedJson);
        System.out.println("Java mapping:");
        System.out.println("  Team class has a 'Coach headCoach' field");
        System.out.println("  Coach is its own class with 'name' and 'yearsExperience'");
        System.out.println();

        // Part 4: Arrays of Objects
        System.out.println("--- Part 4: Arrays of Objects ---");
        System.out.println();

        String arrayJson = """
                {
                  "team_name": "Miami Heat",
                  "roster": [
                    {
                      "name": "Jimmy Butler",
                      "jersey_number": 22,
                      "position": "Forward",
                      "starter": true
                    },
                    {
                      "name": "Bam Adebayo",
                      "jersey_number": 13,
                      "position": "Center",
                      "starter": true
                    },
                    {
                      "name": "Tyler Herro",
                      "jersey_number": 14,
                      "position": "Guard",
                      "starter": false
                    }
                  ]
                }
                """;

        System.out.println("Array of objects example:");
        System.out.println(arrayJson);
        System.out.println("Java mapping:");
        System.out.println("  'roster' array -> List<Player> roster");
        System.out.println("  Each { } in the array becomes one Player object");
        System.out.println();
//
//        // Part 5: Complete Example
//        System.out.println("--- Part 5: Complete JSON Structure ---");
//        System.out.println();
//
//        String completeJson = """
//                {
//                  "team_name": "Miami Heat",
//                  "city": "Miami",
//                  "championships": 3,
//                  "active": true,
//                  "head_coach": {
//                    "name": "Erik Spoelstra",
//                    "years_experience": 16
//                  },
//                  "roster": [
//                    { "name": "Jimmy Butler", "jersey_number": 22, "position": "Forward", "starter": true },
//                    { "name": "Bam Adebayo", "jersey_number": 13, "position": "Center", "starter": true }
//                  ]
//                }
//                """;
//
//        System.out.println("Complete JSON with nested object + array:");
//        System.out.println(completeJson);
//        System.out.println("This maps to 3 Java classes: Team, Coach, Player");
//        System.out.println("  Team has: String, String, int, boolean, Coach, List<Player>");
    }
}
