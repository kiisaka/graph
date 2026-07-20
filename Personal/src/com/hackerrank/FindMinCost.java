package com.hackerrank;

import java.util.List;

public class FindMinCost {

    public static int findMinCost(int timeAvl, List<Integer> startTime, List<Integer> endTime, List<Integer> cost) {
        // Write your code here
        // suboptimal n^2 algorithm first.

        int minCost = Integer.MAX_VALUE;

        if (startTime.size() != endTime.size() ||
                startTime.size() != cost.size()) {
            return -1;
        }
        int size = startTime.size();

        for (int i = 0; i<size; i++)
            for (int k = 1; k<size; k++)
                if (startTime.get(k) >= endTime.get(i) &&
                        startTime.get(i) < endTime.get(k) &&   // They must not overlap
                        ((endTime.get(k) - startTime.get(k) + 1) +
                                (endTime.get(i) - startTime.get(i) + 1)) == timeAvl) {
                    System.out.println(i + "," + k);
                    minCost = Math.min(minCost, cost.get(k) + cost.get(i));
                }
        if (minCost == Integer.MAX_VALUE) return -1;
        return minCost;

    }

    public static void main(String[] args) {
        System.out.println(findMinCost(7, List.of(1,2,4,5), List.of(2,3,8,9), List.of(2,2,5,5)));
    }
}
