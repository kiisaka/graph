package com.leetcode;

public class LongestPalindromicSubstring {
    public String longestPalindrome(String string) {
        if (string == null || string.length() < 2) return string;

        int start = 0, end = 0;

        for (int index = 0; index < string.length(); index++) {
            int length1 = expandFromCentre(string, index, index);
            int length2 = expandFromCentre(string, index, index + 1);
            int maxLength = Math.max(length1, length2);
            if (maxLength > end - start + 1) {
                int mid = maxLength / 2;
                if (maxLength % 2 == 1) {
                    start = index - mid;
                    end = index + mid;
                } else {
                    start = index - mid + 1;
                    end = index + mid;
                }
            }
        }
        return string.substring(start, end + 1);
    }

    private int expandFromCentre(final String string, int left, int right) {
        while (left >= 0
                && right < string.length()
                && string.charAt(left) == string.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
