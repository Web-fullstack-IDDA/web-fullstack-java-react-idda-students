package com.ironhack.methods;

// Demonstrates void methods — methods that perform actions but return nothing.
public class VoidMethodsDemo {

    public static void run() {
        // Simplest void method: no parameters
        printWelcome();

        // Void method with one parameter
        printRecipeTitle("Spaghetti Carbonara");
        printRecipeTitle("Thai Green Curry");

        // Void method with multiple parameters
        printCookingStep(1, "Boil a large pot of salted water");
        printCookingStep(2, "Cook the pasta until al dente");
        printCookingStep(3, "Fry the pancetta in a separate pan");

        // Void method with different parameter types
        displayIngredient("Olive Oil", 2.5, "tablespoons");
        displayIngredient("Garlic", 3.0, "cloves");
        displayIngredient("Black Pepper", 0.5, "teaspoons");

        // Void method with conditional logic
        rateDifficulty(15);
        rateDifficulty(45);
        rateDifficulty(120);
    }

    public static void printWelcome() {
        System.out.println("Welcome to the Recipe Kitchen!");
        System.out.println("Let's learn about methods through cooking.\n");
    }

    public static void printRecipeTitle(String recipeName) {
        System.out.println("--- Recipe: " + recipeName + " ---");
    }

    public static void printCookingStep(int stepNumber, String instruction) {
        System.out.println("  Step " + stepNumber + ": " + instruction);
    }

    public static void displayIngredient(String name, double quantity, String unit) {
        System.out.printf("  Ingredient: %-15s | Qty: %.1f %s%n", name, quantity, unit);
    }

    public static void rateDifficulty(int cookingTimeMinutes) {
        if (cookingTimeMinutes <= 20) {
            System.out.println("  " + cookingTimeMinutes + " min -> Difficulty: Easy (quick meal!)");
        } else if (cookingTimeMinutes <= 60) {
            System.out.println("  " + cookingTimeMinutes + " min -> Difficulty: Medium (worth the effort)");
        } else {
            System.out.println("  " + cookingTimeMinutes + " min -> Difficulty: Hard (chef-level patience needed)");
        }
    }
}
