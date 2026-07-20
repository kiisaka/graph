package org.example;

public class IntegerToRoman {
    public String intToRoman(int num) {
        // Values are listed from largest to smallest.
        // Subtractive forms like 900, 400, 90, 40, 9, and 4 are included
        // so the greedy algorithm works cleanly.
        int[] values = {
                1000, 900, 500, 400,
                100, 90, 50, 40,
                10, 9, 5, 4,
                1
        };

        // Roman symbols corresponding to the values above.
        String[] symbols = {
                "M", "CM", "D", "CD",
                "C", "XC", "L", "XL",
                "X", "IX", "V", "IV",
                "I"
        };

        StringBuilder result = new StringBuilder();

        // Greedily consume the largest possible Roman value at each step.
        for (int i = 0; i < values.length; i++) {
            // Append the current symbol as many times as it fits.
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }

        return result.toString();
    }
}