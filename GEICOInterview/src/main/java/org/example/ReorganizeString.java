package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ReorganizeString {

    private static class CharacterCount {
        char character;
        int count;
        CharacterCount(char character, int count) {
            this.character = character;
            this.count = count;
        }

    public String reorganizeString(String s) {
        final Map<Character, Integer> characterFrequency = new HashMap<>();
        for (char c : s.toCharArray())
            characterFrequency.put(c, characterFrequency.getOrDefault(c, 0) + 1);

        int length = s.length();
        //  Check for any character that appears more than (length + 1) / 2 times. If there is one, it's impossible to reorganize the string.
        for (int frequency : characterFrequency.values()) {
            if (frequency > (length + 1) / 2) {
                return "";
            }
        }

        //  Max heap to store characters by frequency. The character with the highest frequency will be at the top.
        final PriorityQueue<CharacterCount> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.count, a.count)
        );

        for (Map.Entry<Character, Integer> entry : characterFrequency.entrySet()) {
            maxHeap.offer(new CharacterCount(entry.getKey(), entry.getValue()));
        }

        final StringBuilder result = new StringBuilder();
        while (maxHeap.size() > 1) {
            CharacterCount first = maxHeap.poll();
            CharacterCount second = maxHeap.poll();

            // Append the two most frequent characters to the result.
            result.append(first.character);
            result.append(second.character);

            // Decrease their counts and add them back to the heap if they still have remaining counts.
            if (--first.count > 0) {
                maxHeap.offer(first);
            }
            if (--second.count > 0) {
                maxHeap.offer(second);
            }
        }
    }
}
