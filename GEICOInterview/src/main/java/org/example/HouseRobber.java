package org.example;

public class HouseRobber {
    public int rob(int[] nums) {
        // bestTwoBack means the best amount we could rob up to house i - 2.
        int bestTwoBack = 0;

        // bestOneBack means the best amount we could rob up to house i - 1.
        int bestOneBack = 0;

        for (int amount : nums) {
            // Option 1: skip the current house, so total remains bestOneBack.
            int skipCurrent = bestOneBack;

            // Option 2: rob the current house, so we add it to bestTwoBack.
            int robCurrent = bestTwoBack + amount;

            // Best up to the current house.
            int bestCurrent = Math.max(skipCurrent, robCurrent);

            // Move the rolling window forward.
            bestTwoBack = bestOneBack;
            bestOneBack = bestCurrent;
        }

        return bestOneBack;
    }
}It's