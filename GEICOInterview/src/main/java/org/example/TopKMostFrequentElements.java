package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TopKMostFrequentElements {

    public int[][] mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();

        int currentStart = intervals[0][0];
        int currentEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            if (nextStart <= currentEnd) {
                currentEnd = Math.max(currentEnd, nextEnd);
            } else {
                merged.add(new int[]{currentStart, currentEnd});
                currentStart = nextStart;
                currentEnd = nextEnd;
            }
        }

        merged.add(new int[]{currentStart, currentEnd});

        return merged.toArray(new int[merged.size()][]);
    }
}