package com.hackerrank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSortIntervals {

    public static List<List<Integer>> mergeHighDefinitionIntervals(final List<List<Integer>> intervals) {
        // Write your code here

        //  Corner cases
        //  Empty or single interval

        if (intervals.size() <= 1) return intervals;
        List<List<Integer>> cleanedIntervals = new ArrayList<>();

        for (List<Integer> interval: intervals) {
            if (interval.size() != 2) return null;
            //  Check to ensure that first element is smaller than the second
            if (interval.get(0) < interval.get(1)) {
                cleanedIntervals.add(interval);
            } else {
                cleanedIntervals.add(List.of(interval.get(1), interval.get(0)));
            }
        }

        //  intervals with same start: sort using the end first
        cleanedIntervals.sort(Comparator.comparingInt(p->p.get(p.size()-1)));
        cleanedIntervals.sort(Comparator.comparingInt(p->p.get(0)));

        List<List<Integer>> merged = new ArrayList<>();

        merged.add(cleanedIntervals.get(0));

        for (int i=1; i< cleanedIntervals.size(); i++) {
            List<Integer> lastInterval = merged.get(merged.size()-1);
            if (overlap(lastInterval, cleanedIntervals.get(i))) {
                merged.removeLast();
                merged.add(merge(lastInterval, cleanedIntervals.get(i)));
            } else {
                merged.add(cleanedIntervals.get(i));
            }
        }
        return merged;
    }

    private static boolean overlap(final List<Integer> first, final List<Integer> second) {
        return first.get(1) >= second.get(0)
                || second.get(1) <= first.get(0);
    }

    private static List<Integer> merge(final List<Integer> first, final List<Integer> second) {
        //  Assume first and second do overlap.
        //  First contains the second
        if (first.get(0) <= second.get(0)
                && first.get(1) >= second.get(1))
            return first;
        //  They just overlap
        return first.get(1) >= second.get(0)
                    ? List.of(first.get(0), second.get(1))
                    : List.of(second.get(0), first.get(1));
    }

    public static void main(String[] args) {
        List<List<Integer>> testcase1 = new ArrayList<>();
        testcase1.add(List.of(6,7));
        testcase1.add(List.of(1,3));
        testcase1.add(List.of(2,4));
        testcase1.add(List.of(8,10));

        System.out.println(mergeHighDefinitionIntervals(testcase1));

        List<List<Integer>> testcase2 = new ArrayList<>();
        testcase2.add(List.of(1,4));
        testcase2.add(List.of(3,6));
        testcase2.add(List.of(5,8));

        System.out.println(mergeHighDefinitionIntervals(testcase2));

        List<List<Integer>> testcase3 = new ArrayList<>();
        testcase3.add(List.of(1,10));
        testcase3.add(List.of(2,3));
        testcase3.add(List.of(4,8));

        System.out.println(mergeHighDefinitionIntervals(testcase3));

    }
}


