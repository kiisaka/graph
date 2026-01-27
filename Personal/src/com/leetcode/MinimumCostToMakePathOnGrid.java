package com.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MinimumCostToMakePathOnGrid {


    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int minCost(int[][] grid) {

        if (grid.length == 0 || grid[0].length == 0) return -1;
        return bfs(grid, 0, 0);
    }

    private int bfs(int[][] grid, int row, int column) {

        final Deque<int[]> queue = new ArrayDeque<>();
        int[][] distance = new int[grid.length][grid[0].length];
        for (int[] rowArray : distance) Arrays.fill(rowArray, Integer.MAX_VALUE);
        distance[0][0] = 0;
        queue.offerFirst(new int[]{row, column});

        while (!queue.isEmpty()) {
            int[] currentPosition = queue.pop();
            int currentRow = currentPosition[0], currentColumn = currentPosition[1];
            int currentDistance = distance[currentRow][currentColumn];

            for (int i = 0; i < 4; i++) {
                int newRow = currentRow + DIRECTIONS[i][0];
                int newColumn = currentColumn + DIRECTIONS[i][1];

                if (newRow < 0
                        || newRow >= grid.length
                        || newColumn < 0
                        || newColumn >= grid[0].length) continue;

                int edgeCost = (grid[currentRow][currentColumn] == i + 1) ? 0 : 1;
                int currentCost = currentDistance + edgeCost;

                if (currentCost < distance[newRow][newColumn]) {
                    distance[newRow][newColumn] = currentCost;
                    if (edgeCost == 0) queue.offerFirst(new int[]{newRow, newColumn});
                    else queue.offerLast(new int[]{newRow, newColumn});
                }
            }
        }
        return distance[grid.length - 1][grid[0].length - 1];
    }
}