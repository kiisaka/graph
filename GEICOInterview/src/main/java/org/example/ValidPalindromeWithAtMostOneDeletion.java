package org.example;

public class ValidPalindromeWithAtMostOneDeletion {
    public boolean validPalindrome(String s) {
        // Start one pointer at the beginning and one at the end.
        int left = 0;
        int right = s.length() - 1;

        // Move inward while the characters match.
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                // We found the first mismatch.
                // Since we may delete at most one character, try both possibilities:
                //
                // 1. Delete the character at left, then check s[left + 1..right]
                // 2. Delete the character at right, then check s[left..right - 1]
                //
                // If either remaining substring is a palindrome, the whole string is valid.
                return isPalindrome(s, left + 1, right)
                        || isPalindrome(s, left, right - 1);
            }
        }

        // If we never found a mismatch, the string is already a palindrome.
        return true;
    }

    private boolean isPalindrome(String s, int left, int right) {
        // Check whether the substring s[left..right] is a palindrome.
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}