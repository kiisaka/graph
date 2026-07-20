package org.example;

public class KnapsackProblem {
//    You have limited capacity.
//    Each item has a cost/weight and a value.
//    Choose items to maximize value without exceeding capacity.

    public int knapsack(int[] weights, int[] values, int capacity) {
        // bestValueForCapacity[c] means:
        // the maximum value achievable with total capacity c.
        int[] bestValueForCapacity = new int[capacity + 1];

        // Process each item once.
        for (int i = 0; i < weights.length; i++) {
            int weight = weights[i];
            int value = values[i];

            // Scan backward so the current item cannot be reused
            // multiple times in the same iteration.
            for (int c = capacity; c >= weight; c--) {
                // Option 1: do not take this item -> bestValueForCapacity[c]
                // Option 2: take this item -> bestValueForCapacity[c - weight] + value
                bestValueForCapacity[c] = Math.max(bestValueForCapacity[c], bestValueForCapacity[c - weight] + value);
            }
        }

        return bestValueForCapacity[capacity];
    }
}