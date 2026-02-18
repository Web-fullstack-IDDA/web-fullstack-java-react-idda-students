package com.ironhack.methods;

// Demonstrates passing arrays to methods and returning arrays from methods.
public class ArrayMethodsDemo {

    public static void run() {
        // Passing an array to a void method
        int[] mealCalories = {350, 520, 180, 275, 610};
        printAllCalories(mealCalories);

        // Array method that returns a value
        int totalCal = sumCalories(mealCalories);
        System.out.println("Total calories for the day: " + totalCal + " kcal");

        // Finding the highest value in an array
        double[] recipeTimes = {12.5, 45.0, 30.0, 60.5, 22.0};
        double longest = findLongestTime(recipeTimes);
        System.out.println("Longest recipe time: " + longest + " minutes");

        // Array method that returns a boolean
        boolean hasLongRecipe = containsRecipeOver(recipeTimes, 50.0);
        System.out.println("Any recipe over 50 min? " + hasLongRecipe);

        boolean hasVeryLong = containsRecipeOver(recipeTimes, 90.0);
        System.out.println("Any recipe over 90 min? " + hasVeryLong);

        // Method that returns a new array
        String[] ingredients = {"Chicken", "Rice", "Broccoli", "Soy Sauce", "Ginger"};
        String[] labeled = labelIngredients(ingredients);

        System.out.println("Original first item: " + ingredients[0]);
        System.out.println("Labeled first item:  " + labeled[0]);
        printStringItems(labeled);

        // Method that modifies the original array
        double[] portionSizes = {100.0, 250.0, 75.0, 200.0};

        System.out.println("\nBefore doubling portions:");
        printPortions(portionSizes);

        doublePortions(portionSizes);

        // The original array has been changed because arrays are passed by reference
        System.out.println("After doubling portions:");
        printPortions(portionSizes);

        // Passing arrays alongside other parameters
        int[] weekdayCalories = {1800, 2100, 1950, 2200, 1750};
        double avg = computeAverageAboveThreshold(weekdayCalories, 1900);
        System.out.println("\nAverage of days above 1900 kcal: " + avg + " kcal");
    }

    public static void printAllCalories(int[] calories) {
        System.out.println("Calories per meal:");
        for (int i = 0; i < calories.length; i++) {
            System.out.println("  Meal " + (i + 1) + ": " + calories[i] + " kcal");
        }
    }

    public static int sumCalories(int[] calories) {
        int total = 0;
        for (int i = 0; i < calories.length; i++) {
            total += calories[i];
        }
        return total;
    }

    public static double findLongestTime(double[] times) {
        double max = times[0];
        for (int i = 1; i < times.length; i++) {
            if (times[i] > max) {
                max = times[i];
            }
        }
        return max;
    }

    public static boolean containsRecipeOver(double[] times, double threshold) {
        for (int i = 0; i < times.length; i++) {
            if (times[i] > threshold) {
                return true;
            }
        }
        return false;
    }

    public static String[] labelIngredients(String[] ingredients) {
        String[] labeled = new String[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            labeled[i] = "Item " + (i + 1) + ": " + ingredients[i];
        }
        return labeled;
    }

    public static void doublePortions(double[] portions) {
        for (int i = 0; i < portions.length; i++) {
            portions[i] = portions[i] * 2;
        }
    }

    public static void printPortions(double[] portions) {
        for (int i = 0; i < portions.length; i++) {
            System.out.println("  Portion " + (i + 1) + ": " + portions[i] + "g");
        }
    }

    public static void printStringItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.println("  " + items[i]);
        }
    }

    public static double computeAverageAboveThreshold(int[] values, int threshold) {
        int sum = 0;
        int count = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > threshold) {
                sum += values[i];
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return (double) sum / count;
    }
}
