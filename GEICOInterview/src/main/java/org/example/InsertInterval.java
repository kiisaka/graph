package org.example;

import java.util.ArrayList;
import java.util.List;

public class InsertInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1:
        // Add all intervals that are completely before the new interval.
        // These do not overlap because their end is less than newInterval's start.
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2:
        // Merge all intervals that overlap with the new interval.
        // Two intervals overlap if the next interval starts before or at
        // the current newInterval end.
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        // Add the merged new interval.
        result.add(newInterval);

        // Phase 3:
        // Add all remaining intervals, which are completely after the new interval.
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }
}