package com.hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class FindMaxSubstrings {

    /*
     * Complete the 'findMaxSubstrings' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING superset
     *  2. STRING subset
     */

    public static int findMaxSubstrings(String superset, String subset) {

        int[] letterCount = new int[27]; // i=26 for wildcards
        int[] needCount = new int[26];

        for (char c: superset.toCharArray()) {
            if (c == '?') {
                letterCount[26]++;
            } else {
                letterCount[c-'a']++;
            }
        }

        for (char c: subset.toCharArray())
            needCount[c-'a']++;

        int left = 0;
        int right = superset.length() / subset.length();
        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canMake(mid, letterCount, needCount)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }

    private static boolean canMake(int copies, int[] letterCount, int[] needCount) {
        int wildcardCount = letterCount[26];
        int missing = 0;

        for (int i=0; i<26; i++) {
            int required = needCount[i] * copies;
            if (required > letterCount[i]) {
                missing += required - letterCount[i];
                if (missing > wildcardCount)
                    return false;
            }
        }
        return true;

    }

}
