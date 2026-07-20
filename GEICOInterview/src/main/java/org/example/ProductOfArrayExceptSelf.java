package org.example;

public class ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        // result[i] will eventually contain:
        // product of all elements to the left of i
        // times product of all elements to the right of i.
        int[] result = new int[n];

        // prefixProduct is the product of all numbers before the current index.
        int prefixProduct = 1;

        // First pass:
        // Store the product of all elements to the left of each index.
        for (int i = 0; i < n; i++) {
            result[i] = prefixProduct;
            prefixProduct *= nums[i];
        }

        // suffixProduct is the product of all numbers after the current index.
        int suffixProduct = 1;

        // Second pass:
        // Multiply each result[i] by the product of all elements to its right.
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }

        return result;
    }
}