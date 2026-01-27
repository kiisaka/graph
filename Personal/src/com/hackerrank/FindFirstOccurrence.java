package com.hackerrank;

import java.util.List;

public class FindFirstOccurrence {

    //  Corner cases:
    //      num.size() == 0
    //      target not found
    //      target appears multiple times
    //      target appears at the beginning or the end (?)

    public static int findFirstOccurrence(List<Integer> nums, int target) {
        int lo = 0, hi = nums.size() - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (nums.get(mid) < target) {
                lo = mid + 1;
            } else if (nums.get(mid) > target) {
                hi = mid - 1;
            } else {
                ans = mid;      // candidate
                hi = mid - 1;   // search left for earlier occurrence
            }
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
