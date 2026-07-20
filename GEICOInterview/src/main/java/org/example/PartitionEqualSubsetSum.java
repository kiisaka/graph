package org.example;

import java.util.HashSet;
import java.util.Set;

public class PartitionEqualSubsetSum {

    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // If the total sum is odd, we cannot partition it into two equal subsets.
        if (totalSum % 2 != 0) {
            return false;
        }

        int target = totalSum / 2;


        // reachableSums contains subset sums we can make so far.

        Set<Long> reachableSums = new HashSet<>();

        reachableSums.add(0L);

        for (int num : nums) {

            // Create a new set so we do not reuse the same number
            // multiple times in one iteration.

            Set<Long> nextReachableSums = new HashSet<>(reachableSums);

            for (long existingSum : reachableSums) {
                long newSum = existingSum + num;

                // We only care about sums up to targetSum.
                if (newSum <= target) {
                    nextReachableSums.add(newSum);
                }
            }

            reachableSums = nextReachableSums;

            // Early exit if target is reachable.
            if (reachableSums.contains(target)) {
                return true;
            }
        }
        return false;
    }
}
