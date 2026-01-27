package com.leetcode;

import java.util.Arrays;

public class MedianOfTwoSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            int[] tmp = nums1;
            nums1 = nums2;
            nums2 = tmp;
        }

        int m = nums1.length;
        int n = nums2.length;

        int left = 0;
        int right = m;

        int total = m + n;
        int half = (total + 1) / 2;
        while (left <= right) {
            int i = left + (right - left) / 2; //  midpoint in A
            int j = half - i;    //  midpoint in B - i

            //  Look up values at i,j and i-1 and j-1
            int leftA = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int leftB = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];

            int rightA = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int rightB = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (leftA <= rightB && leftB <= rightA) {
                // Correct partition found
                return (total & 1) == 0
                        ? (Math.max(leftA, leftB) + Math.min(rightA, rightB)) / 2.0
                        : Math.max(leftA, leftB);
            } else if (leftA > rightB) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted properly.");
    }


    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{0,10,20}, new int[]{30,40,50,60,70}));
    }
}