package com.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class GetRemovableIndices {

    public static List<Integer> getRemovableIndices(String str1, String str2) {

        if (str1.length() != str2.length() + 1) return List.of(-1);
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < str1.length(); i++) {
            if (compareStrings(str1, str2, i))
                result.add(i);
        }

        return (result.isEmpty()) ? List.of(-1) : result;
        // Write your code here

    }

    public static boolean compareStrings(String s1, String s2, int index) {

        for (int i=0; i<index; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return false;
        }
        for (int i=index; i<s2.length(); i++) {
            if (s1.charAt(i+1) != s2.charAt(i)) return false;
        }
        return true;

    }

    public static void main(String[] args) {
        System.out.println(getRemovableIndices("aabbb", "aabb"));
    }
}