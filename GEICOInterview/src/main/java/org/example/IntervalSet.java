package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class IntervalSet {
    public record Interval(int start, int end) {
        public Interval {
            if (start > end) {
                throw new IllegalArgumentException("start must be <= end");
            }
        }

        public boolean overlapsOrTouches(Interval other) {
            return this.start <= other.end && other.start <= this.end;
        }

        public boolean isBefore(Interval other) {
            return this.end < other.start;
        }

        public Interval mergeWith(Interval other) {
            if (!overlapsOrTouches(other)) {
                throw new IllegalArgumentException("Intervals do not overlap");
            }

            return new Interval(
                    Math.min(this.start, other.start),
                    Math.max(this.end, other.end)
            );
        }
    }
    private final List<Interval> intervals;

    public IntervalSet(List<Interval> intervals) {
        this.intervals = mergeAll(intervals);
    }

    public List<Interval> intervals() {
        return List.copyOf(intervals);
    }

    public IntervalSet insert(Interval newInterval) {
        List<Interval> result = new ArrayList<>();
        int i = 0;

        while (i < intervals.size() && intervals.get(i).isBefore(newInterval)) {
            result.add(intervals.get(i));
            i++;
        }

        Interval merged = newInterval;

        while (i < intervals.size() && intervals.get(i).overlapsOrTouches(merged)) {
            merged = merged.mergeWith(intervals.get(i));
            i++;
        }

        result.add(merged);

        while (i < intervals.size()) {
            result.add(intervals.get(i));
            i++;
        }

        return new IntervalSet(result);
    }

    private static List<Interval> mergeAll(List<Interval> input) {
        if (input.isEmpty()) {
            return List.of();
        }

        List<Interval> sorted = new ArrayList<>(input);
        sorted.sort(Comparator.comparingInt(Interval::start));

        List<Interval> merged = new ArrayList<>();
        Interval current = sorted.get(0);

        for (int i = 1; i < sorted.size(); i++) {
            Interval next = sorted.get(i);

            if (current.overlapsOrTouches(next)) {
                current = current.mergeWith(next);
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);
        return List.copyOf(merged);
    }
}