package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {


    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][2];

        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
        int[][] mergedIntervals = new int[intervals.length][2];

        int start = intervals[0][0];
        int end = intervals[0][1];
        int resultIndex = 0;

        for (int i=1; i< intervals.length; i++) {
            int testStart = intervals[i][0];
            int testEnd = intervals[i][1];

            if (testStart <= end) { // Overlapping, thus extend the current interval
                if (testEnd > end) {
                    end = testEnd;
                }
            } else {
                intervals[resultIndex][0] = start;
                intervals[resultIndex][1] = end;
                resultIndex++;

                start = testStart;
                end = testEnd;
            }
        }
        intervals[resultIndex][0] = start;
        intervals[resultIndex][1] = end;
        resultIndex++;
        return Arrays.copyOf(intervals, resultIndex);
    }
}
