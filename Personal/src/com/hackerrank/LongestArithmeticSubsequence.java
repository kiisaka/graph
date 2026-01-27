package com.hackerrank;

import java.util.*;

public class LongestArithmeticSubsequence {

    /*
     * Complete the 'findLongestArithmeticProgression' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY arr
     *  2. INTEGER k
     */

    public static int findLongestArithmeticProgression(List<Integer> arr, int k) {
        // Write your code here

        //  Corner cases:
        //      1. empty list
        //      2. |list| = 1
        //      3. list isn't sorted - doesn't matter
        //      4. list has duplicates - doesn't matter
        //      5. k=0
        //      6. k<0

        if (k<0) return findLongestArithmeticProgression(arr, -k);
        Map<Integer, Integer> lengths = new HashMap<>();

        int best = 0;
        for (int x : arr) {
            int previous = x - k;
            int length = lengths.getOrDefault(previous, 0) + 1;
            lengths.put(x, length);
            if (length > best) best = length;
        }
        return best;
    }

    public static void main(String[] args) {
        final List<Integer> test1 = List.of(1,1,1,1);
        System.out.println(findLongestArithmeticProgression(test1, 0));

        final List<Integer> test2 = List.of(0,1,2,3);
        System.out.println(findLongestArithmeticProgression(test1, -1));

    }
}