package org.example;

import java.util.Arrays;

public class MinimumRequiredCapacity {

    public int shipWithinDays(int[] weights, int days) {

        // The ship must be able to carry the heaviest single package.
        int minCapacity = Arrays.stream(weights).max().orElseThrow();

        // With capacity equal to total weight, all packages can ship in one day.
        int maxCapacity = Arrays.stream(weights).sum();

        // Binary search for the smallest feasible capacity.
        while (minCapacity < maxCapacity) {

            int candidateCapacity = minCapacity + (maxCapacity - minCapacity) / 2;
            if (canShipWithinDays(weights, days, candidateCapacity)) {
                // Candidate works, so try a smaller capacity.
                maxCapacity = candidateCapacity;

            } else {
                // Candidate is too small, so we need a larger capacity.
                minCapacity = candidateCapacity + 1;
            }
        }

        return minCapacity;
    }

    private boolean canShipWithinDays(int[] weights, int days, int capacity) {

        // Start with day 1.
        int daysUsed = 1;

        // Current load on the ship for the current day.
        int currentLoad = 0;

        for (int weight : weights) {
            // If adding this package would exceed capacity,
            // start a new shipping day.
            if (currentLoad + weight > capacity) {
                daysUsed++;
                currentLoad = 0;
            }

            // Add the package to the current day.
            currentLoad += weight;
            // Early exit if we already exceed the allowed number of days.
            if (daysUsed > days) {
                return false;
            }
        }
        return true;
    }
}
