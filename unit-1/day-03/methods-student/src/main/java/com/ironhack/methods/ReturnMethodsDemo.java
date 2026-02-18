package com.ironhack.methods;

// Demonstrates methods that return values (int, double, String, boolean).
public class ReturnMethodsDemo {

    public static void run() {
        // Method returning int
        int totalCalories = estimateCalories(250, 4);
        System.out.println("Total calories for the meal: " + totalCalories + " kcal");
        System.out.println("Another meal: " + estimateCalories(180, 2) + " kcal");

        // Method returning double
        double scaledFlour = scaleIngredient(200.0, 4, 6);
        System.out.println("Flour needed for 6 servings: " + scaledFlour + "g");

        double scaledSugar = scaleIngredient(50.0, 4, 6);
        System.out.println("Sugar needed for 6 servings: " + scaledSugar + "g");

        // Method returning String
        String pastaDescription = formatRecipeSummary("Pesto Rigatoni", 25, "Italian");
        System.out.println(pastaDescription);

        String curryDescription = formatRecipeSummary("Massaman Curry", 50, "Thai");
        System.out.println(curryDescription);

        // Method returning boolean
        boolean isQuick = isQuickRecipe(15);
        System.out.println("Is a 15-minute recipe quick? " + isQuick);

        boolean isQuick2 = isQuickRecipe(90);
        System.out.println("Is a 90-minute recipe quick? " + isQuick2);

        if (isQuickRecipe(20)) {
            System.out.println("Great choice for a weeknight dinner!");
        }

        // Using return values in calculations
        double butterForTwo = scaleIngredient(100.0, 4, 2);
        double butterForEight = scaleIngredient(100.0, 4, 8);
        double butterDifference = butterForEight - butterForTwo;
        System.out.println("Extra butter needed for 8 vs 2 servings: " + butterDifference + "g");

        // Method with early return
        System.out.println("Prep time category for 5 min: " + categorizePrepTime(5));
        System.out.println("Prep time category for 25 min: " + categorizePrepTime(25));
        System.out.println("Prep time category for 60 min: " + categorizePrepTime(60));
    }

    public static int estimateCalories(int caloriesPerServing, int servings) {
        int total = caloriesPerServing * servings;
        return total;
    }

    public static double scaleIngredient(double originalGrams, int originalServings, int desiredServings) {
        double scaleFactor = (double) desiredServings / originalServings;
        double scaled = originalGrams * scaleFactor;
        return scaled;
    }

    public static String formatRecipeSummary(String name, int prepMinutes, String cuisine) {
        String summary = cuisine + " dish: \"" + name + "\" (prep: " + prepMinutes + " min)";
        return summary;
    }

    public static boolean isQuickRecipe(int prepMinutes) {
        return prepMinutes <= 30;
    }

    public static String categorizePrepTime(int minutes) {
        if (minutes <= 10) {
            return "Minimal prep";
        }
        if (minutes <= 30) {
            return "Moderate prep";
        }
        return "Extensive prep";
    }
}
