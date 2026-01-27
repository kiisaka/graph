package com.hackerrank;

import java.util.List;

public class TargetIndexSearch {

    public static int binarySearch(List<Integer> nums, int target) {

        //  Corner cases
        //  1: nums.size() = 0
        //  2: nums.size() = 1
        //  3: nums not sorted

        // Write your code here
        int left = 0;
        int right = nums.size()-1;
        while (left < right) {
            int center = (left +right) / 2;
            int test = nums.get(center);
            if (test == target)
                return center;
            if (test < target)
                left = center+1;
            else
                right = center;
        }
        return nums.get(left) == target ? left : -1;
    }

    public static void main(String[] args) {
        List<Integer> nums = List.of(1,3,5);
        System.out.println(binarySearch(nums, 2));
        System.out.println(binarySearch(nums, 1));
        System.out.println(binarySearch(nums, 3));

        List<Integer> sameNums = List.of(1,1,1);
        System.out.println(binarySearch(sameNums, 2));

        List<Integer> oneNum = List.of(10);
        System.out.println(binarySearch(oneNum, 10));

    }
}
