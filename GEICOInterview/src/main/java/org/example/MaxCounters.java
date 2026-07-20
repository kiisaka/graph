package org.example;

public class MaxCounters {
    public int[] maxCounters(int numCounters, int[] operations) {
        int[] counters = new int[numCounters];

        // currentMax is the highest actual counter value seen so far.
        int currentMax = 0;

        // baseline is the minimum value every counter should have
        // after the most recent "max counter" operation.
        int baseline = 0;

        for (int operation : operations) {
            if (1 <= operation && operation <= numCounters) {
                int index = operation - 1;

                // Lazily apply the baseline to this counter if needed.
                if (counters[index] < baseline) {
                    counters[index] = baseline;
                }

                // Increment the selected counter.
                counters[index]++;

                // Track the highest actual counter value.
                currentMax = Math.max(currentMax, counters[index]);
            } else if (operation == numCounters + 1) {
                // Instead of updating every counter immediately,
                // just remember the new baseline.
                baseline = currentMax;
            }
        }

        // Apply the final baseline to any counters not touched after the last max operation.
        for (int i = 0; i < counters.length; i++) {
            if (counters[i] < baseline) {
                counters[i] = baseline;
            }
        }

        return counters;
    }
}
