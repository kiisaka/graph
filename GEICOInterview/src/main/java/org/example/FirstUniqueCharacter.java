package org.example;

import java.util.HashMap;
import java.util.Map;

public class FirstUniqueCharacter {
    public int firstUniqChar(String s) {
        int[] counts = new int[26];

        // First pass: count each character.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            counts[c - 'a']++;
        }

        // Second pass: return the first character whose count is 1.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (counts[c - 'a'] == 1) {
                return i;
            }
        }

        return -1;
    }

    public int firstUniqCharUsingMap(String s) {
        Map<Character, Integer> counts = new HashMap<>();

        // First pass: count each character.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Second pass: preserve original order and find the first unique char.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (counts.get(c) == 1) {
                return i;
            }
        }

        return -1;
    }
}