package com.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearch {
    final int[][] neighbours = {{1,0},{0,1},{-1,0},{0,-1}};

    public boolean exist(char[][] board, String word) {
        System.out.println("Looking for " + word);
        if (board.length == 0 || board[0].length == 0) return false;
        boolean found = false;

        for (int row = 0; row < board.length; row++)
            for (int column = 0; column < board[0].length; column++) {
                final List<List<Integer>> visited = new ArrayList<>();
                found = found || exist(board, word, 0, row, column, visited);
                if (found) break;
            }
        return found;

    }

    public boolean exist(char[][] board, String word, int charIndex, int row, int column, List<List<Integer>> visited) {
        visited.forEach(c -> System.out.print(c.get(0) + "," + c.get(1)+ " "));
        System.out.println();
        if (board[row][column] == word.charAt(charIndex) && charIndex == word.length() - 1)
            return true;
        if (! visited.contains(List.of(row, column))
                && board[row][column] == word.charAt(charIndex)) {
            System.out.println("visiting [" + row + "," + column + "] = " + board[row][column]);
            boolean found = false;
            visited.add(List.of(row, column));
            for (int i=0; i<4; i++) {
                if (found) break;
                int newRow = row + neighbours[i][0];
                int newColumn = column + neighbours[i][1];
                if (newRow >= 0 && newColumn >= 0
                        && newRow < board.length && newColumn < board[0].length
                        && charIndex < word.length()-1)
                    found = found || exist(board, word, charIndex+1, newRow, newColumn, visited);

            }
            return found;
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A','B','C','E'},{'S','F','E','S'},{'A','D','E','E'}};
        final WordSearch solution = new WordSearch();

        solution.exist(board, "ABCESEEEFS");
    }
}
