package com.leetcode;

import java.util.ArrayList;
import java.util.List;

public class PhoneLetterCombinations {

    private final static String[] LETTERS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {

        final List<String> result = new ArrayList();
        if (digits == null || digits.isEmpty()) return result;

        final StringBuilder sb = new StringBuilder();

        addCombinations(digits, 0, sb, result);

        return result;
    }

    private void addCombinations(final String digits, int index, StringBuilder sb, List<String> result) {
        if (index == digits.length()) {
            result.add(sb.toString());
            return;
        }

        final char digit = digits.charAt(index);
        final String letters = LETTERS[digit - '0'];
        if (letters.isEmpty()) {
            addCombinations(digits, index + 1, sb, result);
            return;
        }

        for (int i = 0; i < letters.length(); i++) {
            sb.append(letters.charAt(i));
            addCombinations(digits, index + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);   // Why is this necessary?
        }
    }
}