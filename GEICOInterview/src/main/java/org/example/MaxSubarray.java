package org.example;

public class MaxSubarray {
    public int maxSubArray(int[] nums) {
        // bestEndingHere is the best sum of a subarray ending at the current index.
        int bestEndingHere = nums[0];

        // bestOverall is the best subarray sum seen anywhere so far.
        int bestOverall = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // Either extend the previous subarray or start fresh at current number.
            bestEndingHere = Math.max(num, bestEndingHere + num);

            // Update global best.
            bestOverall = Math.max(bestOverall, bestEndingHere);
        }

        return bestOverall;
    }
}
