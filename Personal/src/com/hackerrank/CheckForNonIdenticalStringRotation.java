package com.hackerrank;

public class CheckForNonIdenticalStringRotation {

    /*
     * Complete the 'isNonTrivialRotation' function below.
     *
     * The function is expected to return a BOOLEAN.
     * The function accepts following parameters:
     *  1. STRING s1
     *  2. STRING s2
     */

    public static boolean isNonTrivialRotation(String s1, String s2) {
        // Write your code here
        // Corner cases
        //  |s1| != |s2|
        if (s1.length() != s2.length()) return false;
        //  |s1| = 0;
        if (s1.length() <= 1) return false;
        //  s1==s2
        if (s1.equals(s2)) return false;

        return (s1+s1).contains(s2);
    }

    public static void main(String[] args) {
        System.out.println(isNonTrivialRotation("abcde","bcdea"));
    }
}
