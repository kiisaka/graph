package com.leetcode;

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        if (!wordList.contains(endWord)) return 0;

        Map<String, Set<String>> adjacentPairs = new HashMap<>();
        wordList.add(beginWord);

        return bfs(wordList, adjacentPairs, beginWord, endWord);
    }

    private boolean adjacentPair(final String word1, final String word2) {
        if (word1.length() != word2.length()) return false;

        int diffs = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i))
                diffs++;
        }

        return diffs == 1;
    }

    private int bfs(
            List<String> wordList,
            Map<String, Set<String>> adjacentPairs,
            String beginWord, String endWord) {

        final ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add(beginWord);

        final ArrayDeque<Integer> distance = new ArrayDeque<>();
        distance.add(1);

        final Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Integer currentDistance = distance.poll();
            System.out.println("current word = " + current + ", distance = " + currentDistance);
            if (current.equals(endWord)) return currentDistance;

            for (String adjacentWord : wordList) {
                if (adjacentPair(current, adjacentWord) && visited.add(adjacentWord)) {
                    queue.add(adjacentWord);
                    distance.add(currentDistance + 1);
                }
            }
        }
        return 0;
    }
}