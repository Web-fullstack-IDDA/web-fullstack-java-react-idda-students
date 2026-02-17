package com.ironhack.datatypes;

// Demonstrates if/else, switch (classic and enhanced), ternary operator, and logical operators.
public class ConditionalsDemo {
    public static void run() {
        // --- Basic if/else ---
        int loyaltyPoints = 750;

        if (loyaltyPoints >= 500) {
            System.out.println("You qualify for a free pastry!");
        } else {
            System.out.println("Earn " + (500 - loyaltyPoints) + " more points for a free pastry.");
        }

        // --- if / else if / else (Membership tiers) ---
        int memberPoints = 2800;
        String tier;

        if (memberPoints >= 5000) {
            tier = "Platinum";
        } else if (memberPoints >= 2500) {
            tier = "Gold";
        } else if (memberPoints >= 1000) {
            tier = "Silver";
        } else if (memberPoints >= 250) {
            tier = "Bronze";
        } else {
            tier = "Standard";
        }
        System.out.println("\nMember points: " + memberPoints + " -> Tier: " + tier);

        // --- Logical operators ---
        boolean isMember = true;
        boolean isHappyHour = true;
        double drinkPrice = 5.50;

        // AND (&&): both must be true
        if (isMember && isHappyHour) {
            drinkPrice *= 0.75;
            System.out.println("\nMember + Happy Hour discount! Price: $" + drinkPrice);
        }

        // OR (||): at least one must be true
        boolean isWeekend = false;
        boolean isHoliday = true;
        if (isWeekend || isHoliday) {
            System.out.println("Special weekend/holiday menu available!");
        }

        // NOT (!): inverts the condition
        boolean isSoldOut = false;
        if (!isSoldOut) {
            System.out.println("Item is available!");
        }

        // --- Nested conditionals ---
        int customerAge = 22;
        boolean hasStudentId = true;

        if (customerAge < 26) {
            if (hasStudentId) {
                System.out.println("\nStudent discount: 15% off all books!");
            } else {
                System.out.println("\nYoung adult discount: 5% off all books!");
            }
        } else {
            System.out.println("\nStandard pricing applies.");
        }

        boolean isStudentEligible = customerAge < 26 && hasStudentId;
        boolean isYoungAdult = customerAge < 26 && !hasStudentId;

        // --- Classic switch (day of the week specials) ---
        int dayOfWeek = 3;
        String dailySpecial;

        switch (dayOfWeek) {
            case 1:
                dailySpecial = "Mocha Monday - All mochas $3.99";
                break;
            case 2:
                dailySpecial = "Tea Tuesday - Buy 1 get 1 free tea";
                break;
            case 3:
                dailySpecial = "Waffle Wednesday - Free waffle with any large drink";
                break;
            case 4:
                dailySpecial = "Throwback Thursday - Vintage blend $2.99";
                break;
            case 5:
                dailySpecial = "Frappuccino Friday - All frappes 20% off";
                break;
            case 6:
            case 7:
                dailySpecial = "Weekend Brunch Special - Pastry + Coffee combo $8.99";
                break;
            default:
                dailySpecial = "No special today";
                break;
        }
        System.out.println("\nToday's special: " + dailySpecial);

        // --- Enhanced switch (Java 14+, arrow syntax, no break needed) ---
        String drinkSize = "L";
        double sizePrice = switch (drinkSize) {
            case "S" -> 3.50;
            case "M" -> 4.50;
            case "L" -> 5.50;
            case "XL" -> 6.50;
            default -> {
                System.out.println("Unknown size, defaulting to Medium");
                yield 4.50;     // 'yield' returns a value from a block in switch expression
            }
        };
        System.out.println("Size " + drinkSize + " price: $" + sizePrice);

        // --- Switch with Strings ---
        String bookGenre = "mystery";

        String shelfLocation = switch (bookGenre.toLowerCase()) {
            case "fiction" -> "Aisle 1, Shelf A";
            case "mystery" -> "Aisle 1, Shelf C";
            case "science" -> "Aisle 2, Shelf A";
            case "history" -> "Aisle 2, Shelf B";
            case "comics" -> "Aisle 3, Shelf A";
            default -> "Information Desk - ask staff";
        };
        System.out.println("\"" + bookGenre + "\" books are at: " + shelfLocation);

        // --- Ternary operator: condition ? valueIfTrue : valueIfFalse ---
        boolean isLoyalCustomer = true;
        double tipSuggestion = isLoyalCustomer ? 0.20 : 0.15;
        System.out.println("\nSuggested tip: " + (tipSuggestion * 100) + "%");

        double coffeePrice = 4.75;
        double finalPrice = isMember ? coffeePrice * 0.90 : coffeePrice;
        System.out.println("Coffee price (member discount applied): $" + finalPrice);

        // --- Combining concepts: a complete order decision tree ---
        int itemsInCart = 4;
        boolean hasCoupon = true;
        double cartTotal = 32.50;

        boolean freeShipping = cartTotal >= 25.0;
        System.out.println("\nCart total: $" + cartTotal);
        System.out.println("Free shipping: " + (freeShipping ? "Yes" : "No"));

        if (hasCoupon && cartTotal >= 20.0) {
            double couponDiscount = 5.0;
            cartTotal -= couponDiscount;
            System.out.println("Coupon applied: -$" + couponDiscount);
        }

        String bulkMessage = switch (itemsInCart) {
            case 1, 2 -> "No bulk discount";
            case 3, 4 -> "5% bulk discount applied";
            default -> itemsInCart >= 5 ? "10% bulk discount applied" : "No bulk discount";
        };
        System.out.println(bulkMessage);

        if (itemsInCart >= 5) {
            cartTotal *= 0.90;
        } else if (itemsInCart >= 3) {
            cartTotal *= 0.95;
        }
        System.out.println("Final total: $" + cartTotal);
    }
}
