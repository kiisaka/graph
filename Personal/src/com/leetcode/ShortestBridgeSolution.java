package com.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class ShortestBridgeSolution {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int shortestBridge(int[][] grid) {
        int n = grid.length;
        boolean[][] seen = new boolean[n][n];
        Deque<int[]> q = new ArrayDeque<>();

        // 1) Find first island and mark it, adding all its cells to the queue
        boolean found = false;
        for (int r = 0; r < n && !found; r++) {
            for (int c = 0; c < n && !found; c++) {
                if (grid[r][c] == 1) {
                    markIsland(grid, seen, r, c, q);
                    found = true;
                }
            }
        }

        // 2) Multi-source BFS from all cells of island #1
        int steps = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int r = cur[0], c = cur[1];

                for (int[] d : DIRS) {
                    int nr = r + d[0], nc = c + d[1];
                    if (nr < 0 || nr >= n || nc < 0 || nc >= n || seen[nr][nc]) continue;

                    // If we reach a '1' not in the first island, that's island #2
                    if (grid[nr][nc] == 1) return steps;

                    // Otherwise it's water: expand into it
                    seen[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
            steps++;
        }

        return -1; // should not happen if input guarantees two islands
    }

    // Iterative DFS to mark all cells of the first island and seed the BFS queue
    private void markIsland(int[][] grid, boolean[][] seen, int sr, int sc, Deque<int[]> q) {
        int n = grid.length;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{sr, sc});
        seen[sr][sc] = true;

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            int r = cur[0], c = cur[1];

            q.offer(new int[]{r, c}); // seed BFS with all island cells

            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if (seen[nr][nc]) continue;
                if (grid[nr][nc] == 0) continue;

                seen[nr][nc] = true;
                stack.push(new int[]{nr, nc});
            }
        }
    }
}
