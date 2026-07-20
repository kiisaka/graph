package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LengthLongestSubstringWithNoDuplicate {
    public int lengthOfLongestSubstring(String s) {
        // Maps each character to the most recent index where we saw it.
        Map<Character, Integer> lastSeen = new HashMap<>();

        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If c was seen inside the current window, move left
            // just past its previous position.
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }

            // Record the latest position of c.
            lastSeen.put(c, right);

            // Current window is s[left..right].
            best = Math.max(best, right - left + 1);
        }

        return best;
    }



    public int lengthOfLongestSubstringASCIIOnly(String s) {

        // Maps each character to the most recent index where it appeared.
        Map<Character, Integer> lastSeen = new HashMap<>();

        // Left boundary of the current sliding window.
        int left = 0;

        // Best length found so far.
        int best = 0;

        // Expand the right boundary one character at a time.
        for (int right = 0; right < s.length(); right++) {

            char c = s.charAt(right);

            // If this character was seen inside the current window,
            // move the left boundary just past its previous occurrence.
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }

            // Update this character's most recent position.
            lastSeen.put(c, right);

            // The current valid window is s[left..right].
            best = Math.max(best, right - left + 1);
        }

        return best;

    }
}