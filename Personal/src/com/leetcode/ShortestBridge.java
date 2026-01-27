package com.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class ShortestBridge {

    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int shortestBridge(int[][] grid) {
        //  Find and mark the first island (DFS)
        //  Expand outward from that island at once (BFS)
        //  Extend until 1 not in the first island is found.

        int n = grid.length;

        if (n == 0 || n != grid[0].length) throw new IllegalArgumentException();

        boolean firstIsland[][] = new boolean[n][n];
        final Deque<Integer> queue = new ArrayDeque<>();

        //  Mark the first island. Scan and find the first "1"

        boolean found = false;
        for (int row = 0; row < n && !found; row++)
            for (int column = 0; column < n && !found; column++)
                if (grid[row][column] == 1) {
                    markFirstIsland(grid, firstIsland, row, column, queue);
                    found = true;
                }

        int stepCount = 0;
        while (!queue.isEmpty()) {
            int max = queue.size();
            for (int i = 0; i < max; i++) {
                int index = queue.poll();
                int row = index / n, column = index % n;

                for (int[] d : DIRECTIONS) {
                    int newRow = row + d[0], newColumn = column + d[1];
                    if (newRow < 0 || newRow >= n || newColumn < 0 || newColumn >= n || firstIsland[newRow][newColumn])
                        continue;
                    if (grid[newRow][newColumn] == 1) return stepCount;
                    firstIsland[newRow][newColumn] = true;
                    queue.offer(n * newRow + newColumn);
                }
            }
            stepCount++;
        }

        return -1;
    }

    private void markFirstIsland(int[][] grid, boolean[][] firstIsland, int row, int column, Deque<Integer> queue) {
        int n = grid.length;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(row * n + column);
        firstIsland[row][column] = true;
        while (!stack.isEmpty()) {
            int current = stack.pop();
            int currentRow = current / n, currentColumn = current % n;

            boolean isOnShore = false;
            for (int[] d : DIRECTIONS) {
                int newRow = currentRow + d[0], newColumn = currentColumn + d[1];
                //  Check out of bound
                if (newRow < 0 || newRow >= n || newColumn < 0 || newColumn >= n) continue;
                if (firstIsland[newRow][newColumn]) continue;
                if (grid[newRow][newColumn] == 0) {
                    isOnShore = true;
                } else {
                    firstIsland[newRow][newColumn] = true;
                    stack.push(newRow * n + newColumn);
                }
            }
            // Keep track of where the first island is
            if (isOnShore)
                queue.offer(currentRow * n + currentColumn);

        }
    }
}
