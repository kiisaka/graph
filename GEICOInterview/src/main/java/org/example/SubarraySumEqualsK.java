package org.example;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {

    public int subarraySum(int[] nums, int k) {

        // Use a Map because using previx[] would become O(n^2) to find the count of subarrays with sum k.

        Map<Integer, Integer> prefixCounts = new HashMap<>();

        prefixCounts.put(0, 1);

        int prefix = 0;

        int count = 0;

        for (int num : nums) {

            prefix += num;

            count += prefixCounts.getOrDefault(prefix - k, 0);

            prefixCounts.put(prefix, prefixCounts.getOrDefault(prefix, 0) + 1);

        }

        return count;
    }
}
