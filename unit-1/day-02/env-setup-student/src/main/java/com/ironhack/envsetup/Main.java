package com.ironhack.envsetup;

/**
 * My first Java program — demonstrates program output and string concatenation.
 * Java programs always start executing from the main method.
 */
public class Main {

    // public: accessible by JVM | static: no object needed | void: returns nothing
    // String[] args: command-line arguments (required even if unused)
    public static void main(String[] args) {

        // println() prints text and moves to the next line
        System.out.println("=== Welcome to Ironhack Full-Stack Development ===");
        System.out.println();

        // print() prints text but stays on the same line
        System.out.print("Course: ");
        System.out.println("Web Development Bootcamp");

        System.out.println();
    }
}
