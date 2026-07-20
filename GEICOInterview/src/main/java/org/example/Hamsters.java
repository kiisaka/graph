package org.example;

public class Hamsters {

    public int minimumBuckets(String hamsters) {
        // Convert to a mutable array so we can mark bucket placements.
        char[] cells = hamsters.toCharArray();

        // Count how many buckets we place.
        int buckets = 0;

        for (int i = 0; i < cells.length; i++) {
            // Empty cells do not need to be fed.
            if (cells[i] != 'H') {
                continue;
            }

            // If there is already a bucket immediately to the left,
            // this hamster is already fed.
            if (i > 0 && cells[i - 1] == 'B') {
                continue;
            }

            // Prefer placing a bucket to the right.
            // This may feed both the current hamster and a future hamster.
            if (i + 1 < cells.length && cells[i + 1] == '.') {
                cells[i + 1] = 'B';
                buckets++;
                continue;
            }

            // If right is unavailable, try placing a bucket to the left.
            // This is less useful because it cannot help future hamsters.
            if (i > 0 && cells[i - 1] == '.') {
                cells[i - 1] = 'B';
                buckets++;
                continue;
            }

            // No adjacent empty spot is available.
            return -1;
        }

        return buckets;
    }
}