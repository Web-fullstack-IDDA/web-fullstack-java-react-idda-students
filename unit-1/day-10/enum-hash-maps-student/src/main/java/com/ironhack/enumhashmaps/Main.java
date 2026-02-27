package com.ironhack.enumhashmaps;

import java.util.HashMap;
import java.util.Map;

// Demonstrates Enums and HashMaps.
public class Main {

    // Switch helper method
    public static String getStatusMessage(Status status) {
        switch (status) {
            case ONLINE:  return "User is available";
            case IDLE:    return "User is away";
            case OFFLINE: return "User is offline";
            default:      return "Unknown status";
        }
    }

    public static void main(String[] args) {

        // =============================================================
        // 1. ENUMS: TYPE-SAFE CONSTANTS
        // =============================================================
        System.out.println("=== Enums: Type-Safe Constants ===");

        Status current = Status.ONLINE;
        System.out.println(current);  // ONLINE

        // Convert a String to an enum (case-sensitive — must match exactly)
        Status fromString = Status.valueOf("IDLE");
        System.out.println("From string: " + fromString);

        System.out.println();

        // =============================================================
        // 2. ENUMS WITH SWITCH
        // =============================================================
        System.out.println("=== Enums with switch ===");

        // NOTE: Inside the switch, use just the constant name (ONLINE), not Status.ONLINE
        System.out.println(getStatusMessage(Status.ONLINE));
        System.out.println(getStatusMessage(Status.IDLE));
        System.out.println(getStatusMessage(Status.OFFLINE));

        System.out.println();

        // =============================================================
        // 3. ENUMS IN CLASSES
        // =============================================================
        System.out.println("=== Enums in Classes ===");

        User alice = new User("Alice");    // defaults to ONLINE
        User bob = new User("Bob");
        User carol = new User("Carol");

        System.out.println(alice);
        System.out.println(bob);
        System.out.println(carol);

        // Modify status through the setter — compiler rejects invalid values
        bob.setStatus(Status.IDLE);
        carol.setStatus(Status.OFFLINE);
        System.out.println("Bob updated:   " + bob);
        System.out.println("Carol updated: " + carol);

        System.out.println();

        // =============================================================
        // 4. COMPARING ENUMS
        // =============================================================
        System.out.println("=== Comparing Enums ===");

        Status s1 = Status.ONLINE;
        Status s2 = Status.ONLINE;
        Status s3 = Status.OFFLINE;

        // Prefer == for enums — it's null-safe
        System.out.println("s1 == s2: " + (s1 == s2));          // true
        System.out.println("s1 == s3: " + (s1 == s3));          // false
        System.out.println();

        // =============================================================
        // 5. HASHMAP BASICS
        // =============================================================
        System.out.println("=== HashMap Basics ===");

        // HOW HASHMAP (usually) WORKS UNDER THE HOOD:
        //  1. hashCode()  — computes a bucket index from the key (not the value).
        //  2. Bucket      — goes directly to that bucket; same key always same bucket.
        //  3. equals()    — scans only that bucket, examples:
        //                      put() replaces a match
        //                      get() returns the value on match
        //                      remove() deletes the entry.
   

        Map<String, String> capitalCities = new HashMap<>();

        capitalCities.put("Spain", "Madrid");
        capitalCities.put("Japan", "Tokyo");
        capitalCities.put("Brazil", "Brasilia");

        System.out.println("Capital cities: " + capitalCities);

        // get() — returns null if key doesn't exist
        System.out.println("Japan's capital: " + capitalCities.get("Japan"));    // Tokyo
        System.out.println("France's capital: " + capitalCities.get("France"));  // null

        // getOrDefault() — safer alternative to get()
        System.out.println("France (safe): " + capitalCities.getOrDefault("France", "Unknown"));

        System.out.println("Has 'Spain'? " + capitalCities.containsKey("Spain"));      // true
        System.out.println("Has 'France'? " + capitalCities.containsKey("France"));    // false
        System.out.println("Has 'Madrid'? " + capitalCities.containsValue("Madrid"));  // true

        // stored counter, not fresh recount
        System.out.println("Size: " + capitalCities.size());  // 3

        // Duplicate keys overwrite the old value silently
        capitalCities.put("Brazil", "Rio");
        System.out.println("Brazil after overwrite: " + capitalCities.get("Brazil"));  // Rio

        // Restore correct value and remove an entry
        capitalCities.put("Brazil", "Brasilia");
        System.out.println("Brazil after second overwrite: " + capitalCities.get("Brazil")); // Brasilia
        capitalCities.remove("Brazil");
        System.out.println("After removing Brazil: " + capitalCities);
        System.out.println("Size after removal: " + capitalCities.size());  // 2

        System.out.println();

        // =============================================================
        // 6. HASHMAP ITERATION
        // =============================================================
        System.out.println("=== HashMap Iteration ===");

        // entrySet() — iterate over both keys and values
        System.out.println("All capitals:");
        for (Map.Entry<String, String> entry : capitalCities.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        // === keySet() and values() ===
        System.out.println("Just countries: " + capitalCities.keySet());
        System.out.println("Just capitals:  " + capitalCities.values());

        System.out.println();

        // =============================================================
        // 7. HASHMAP WITH OBJECTS
        // =============================================================
        System.out.println("=== HashMap with Objects ===");

        Map<String, Country> countryMap = new HashMap<>();
        countryMap.put("Japan", new Country("Japan", "Tokyo", 125000000));
        countryMap.put("Spain", new Country("Spain", "Madrid", 47000000));
        countryMap.put("Brazil", new Country("Brazil", "Brasilia", 215000000));

        // lookup by key
        Country japan = countryMap.get("Japan");
        System.out.println("Capital of Japan: " + japan.getCapital());
        System.out.println("Population:       " + japan.getPopulation());

        System.out.println("All countries:");
        for (Map.Entry<String, Country> entry : countryMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

    }
}
