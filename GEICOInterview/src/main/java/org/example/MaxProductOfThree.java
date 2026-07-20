package org.example;

import java.util.Arrays;

public class MaxProductOfThree {
    public int maxProductOfThree(int[] nums) {
        // Sort so we can easily access the largest and smallest values.
        Arrays.sort(nums);

        int n = nums.length;

        // Candidate 1:
        // product of the three largest numbers.
        int productOfThreeLargest = nums[n - 1] * nums[n - 2] * nums[n - 3];

        // Candidate 2:
        // product of the two smallest numbers and the largest number.
        // This matters because two negative numbers produce a positive product.
        int productOfTwoSmallestAndLargest = nums[0] * nums[1] * nums[n - 1];

        return Math.max(productOfThreeLargest, productOfTwoSmallestAndLargest);
    }
}