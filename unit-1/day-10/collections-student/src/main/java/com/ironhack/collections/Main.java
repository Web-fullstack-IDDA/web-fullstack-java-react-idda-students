package com.ironhack.collections;

// Demonstrates ArrayList and HashSet using Cities, Students, and Courses domains.

import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        // =============================================================
        System.out.println("=== Collections Overview ===");
        // =============================================================

        ArrayList<String> cities = new ArrayList<>();
        HashSet<String> courses = new HashSet<>();

        cities.add("Lisbon");
        cities.add("Berlin");
        cities.add("Baku");
        courses.add("CyberSecurity");
        courses.add("Web Development");
        courses.add("Data & Analytics");

        System.out.println("Cities (ArrayList): " + cities);   // preserves insertion order
        System.out.println("Courses (HashSet):  " + courses);  // order is NOT guaranteed
        System.out.println();

        // =============================================================
        System.out.println("=== Generics & Autoboxing ===");
        // =============================================================

        // Generics enforce type safety
        ArrayList<Integer> ages = new ArrayList<>();

        // Autoboxing: int -> Integer automatically
        ages.add(20);
        ages.add(25);
        ages.add(30);

        // Unboxing: Integer -> int automatically
        int firstAge = ages.get(0);

        System.out.println("Ages:      " + ages);
        System.out.println("First age: " + firstAge);
        System.out.println();

        // =============================================================
        System.out.println("=== ArrayList Operations ===");
        // =============================================================

        // clear() -- removes all
        cities.clear();

        // add(element) -- appends to the end
        cities.add("Lisbon");
        cities.add("Berlin");
        cities.add("Tokyo");
        cities.add("Lisbon");           // duplicates allowed
        System.out.println("Cities after adds: " + cities);

        // add(index, element) -- inserts at position, shifts elements right
        cities.add(1, "Paris");
        System.out.println("After insert at 1: " + cities);

        // get(index) -- retrieve by position (0-based)
        System.out.println("First city: " + cities.get(0));

        // set(index, element) -- replace element at position
        cities.set(1, "Rome");
        System.out.println("After set(1):      " + cities);

        // size()
        System.out.println("Cities size: " + cities.size());

        // contains(element) -- uses .equals()
        System.out.println("Contains 'Berlin'? " + cities.contains("Berlin"));
        System.out.println("Contains 'Madrid'? " + cities.contains("Madrid"));

        // isEmpty()
        System.out.println("Is empty? " + cities.isEmpty());

        // remove(index) -- removes by position, returns removed element
        String removed = cities.remove(0);
        System.out.println("Removed by index: " + removed);
        System.out.println("Cities now:       " + cities);

        // remove(object) -- removes first occurrence, returns boolean
        boolean wasRemoved = cities.remove("Lisbon");
        System.out.println("Removed 'Lisbon'? " + wasRemoved);
        System.out.println("Cities now:       " + cities);

        System.out.println("Size: " + cities.size());
        System.out.println();

        // =============================================================
        System.out.println("=== ArrayList with Custom Objects ===");
        // =============================================================

        ArrayList<Student> roster = new ArrayList<>();
        roster.add(new Student("Alice", 85));
        roster.add(new Student("Bob", 92));
        roster.add(new Student("Diana", 95));

        System.out.println("Student roster:");
        for (Student student : roster) {
            System.out.println("  - " + student);
        }

        // ArrayList<Person> accepts any subclass -- Student IS-A Person
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Charlie"));
        people.add(new Student("Alice", 85));
        people.add(new Student("Bob", 92));

        System.out.println("Mixed Person/Student list:");
        for (Person p : people) {
            System.out.println("  - " + p);
        }
        System.out.println();

        // =============================================================
        System.out.println("=== HashSet Operations ===");
        // =============================================================

        // HOW HASHSET (usually) WORKS UNDER THE HOOD:
        //  1. hashCode()  — computes a number from the element, mapped to a bucket index.
        //  2. Bucket      — goes directly to that bucket; same element always same bucket.
        //  3. equals()    — scans only that bucket, examples:
        //                      add() if match, rejects a match, if not adds
        //                      contains() returns true on match
        //                      remove() deletes the match


        courses.clear();

        // add() returns true if new, false if duplicate
        courses.add("Java Fundamentals");
        courses.add("Web Development");
        courses.add("Java Fundamentals");  // duplicate!

        System.out.println("Courses: " + courses);
        System.out.println("Size:    " + courses.size());  // 2, not 3

        // contains() — fast lookup
        System.out.println("Contains 'Web Development'? " + courses.contains("Web Development"));
        System.out.println("Contains 'Data Science'?    " + courses.contains("Data Science"));

        // remove() — returns true if element was present
        courses.remove("Web Development");
        System.out.println("Courses now: " + courses);

        System.out.println();

        // =============================================================
        System.out.println("=== HashSet: Finding Unique Values ===");
        // =============================================================

        ArrayList<String> log = new ArrayList<>();
        log.add("Alice");
        log.add("Bob");
        log.add("Alice");   // duplicate

        System.out.println("Sign-in log (with duplicates): " + log);
        System.out.println("Sign-ins: " + log.size());  // 3

        // Pass a list to HashSet constructor to deduplicate
        HashSet<String> unique = new HashSet<>(log);

        System.out.println("Unique users: " + unique);
        System.out.println("Unique: " + unique.size());  // 2
    }
}
