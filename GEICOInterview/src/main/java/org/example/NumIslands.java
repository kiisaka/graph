package org.example;

import java.util.ArrayDeque;
import java.util.Queue;

public class NumIslands {

    private static int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == '1') {
                    islands++;

                    Queue<int[]> queue = new ArrayDeque<>();
                    queue.offer(new int[] { row, col });
                    grid[row][col] = '0'; // mark visited

                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        int r = current[0];
                        int c = current[1];

                        for (int[] d : directions) {
                            int nr = r + d[0];
                            int nc = c + d[1];

                            if (
                                    nr >= 0 && nr < rows &&
                                            nc >= 0 && nc < cols &&
                                            grid[nr][nc] == '1'
                            ) {
                                grid[nr][nc] = '0'; // mark visited
                                queue.offer(new int[] { nr, nc });
                            }
                        }
                    }
                }
            }
        }

        return islands;
    }
}
