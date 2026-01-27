package com.neetcode;

import java.util.*;
import java.util.stream.Collectors;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        // Corner cases:
        //  nums.length == 0;
        //  k == 0;

        // Define a map to capture counts by number;
        final Map<Integer,Integer> counters = new HashMap<>();

        for (int number: nums) {
            counters.put(number, counters.getOrDefault(number,0) + 1);
        }
        //  Now, get a list of numbers by count
        final Map<Integer, Set<Integer>> numsByCount = new HashMap<>();
        counters.forEach((number,count) -> {
            numsByCount.putIfAbsent(count, new HashSet<>());
            numsByCount.get(count).add(number);
        });

        // Now collect the numbers from the highest count;

        final List<Integer> counts = new ArrayList<>(numsByCount.keySet());
        Collections.sort(counts);
        Collections.reverse(counts);
        int i=0;
        int[] result = new int[k];
        for (int count: counts) {
            for (int num: numsByCount.get(count)) {
                if (i==k) break;
                result[i++] = num;
            }
        }

        return result;
    }
    public static void main(String[] args) {
        final TopKFrequentElements solution = new TopKFrequentElements();

        solution.topKFrequent(new int[]{1,2,2,3,3,3}, 2);
    }
}
