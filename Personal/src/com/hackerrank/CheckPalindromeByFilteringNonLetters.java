package com.hackerrank;

public class CheckPalindromeByFilteringNonLetters {

    /*
     * Complete the 'isAlphabeticPalindrome' function below.
     *
     * The function is expected to return a BOOLEAN.
     * The function accepts STRING code as parameter.
     */

    public static boolean isAlphabeticPalindrome(String code) {
        // Write your code here
        // Strip non-alphabets
        final String alphabetsOnly = code.codePoints()
                .filter(Character::isAlphabetic)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString()
                .toLowerCase();
        return isPalindrome(alphabetsOnly);
    }

    public static boolean isPalindrome(final String code) {
        for (int i = 0; i<=(code.length()+1) / 2; i++) {
            if (code.charAt(i) != code.charAt(code.length()-i-1))
                return false;
        }
        return true;
    }

    public static void main(final String[] args) {
        System.out.println(isAlphabeticPalindrome("abc123cba"));
    }
}
