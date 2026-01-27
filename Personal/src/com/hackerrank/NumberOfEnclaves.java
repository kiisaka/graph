package com.hackerrank;

import java.util.ArrayDeque;
import java.util.Deque;

class NumberOfEnclaves {

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public int numEnclaves(int[][] grid) {

        final int x = grid.length, y = grid[0].length;
        if (x <= 1 || y <=1) return 0;

        final Deque<int[]> queue = new ArrayDeque<>();
        //  eliminate all boundary land cells

        for (int i=0; i<x-1; i++) {
            if (grid[i][0] == 1) {
                grid[i][0] = 0;
                queue.add(new int[]{i,0});
            }
            if (y-1 != 0 && grid[i][y-1] == 1) {
                grid[i][y-1] = 0;
                queue.add(new int[]{i,y-1});}
        }

        for (int i=1; i<y-2; i++) {
            if (grid[0][i] == 1) {
                grid[0][i] = 0;
                queue.add(new int[]{0,i});
            }
            if (x-1 != 0 && grid[x-1][i] == 1) {
                grid[x-1][i] = 0;
                queue.add(new int[]{x-1,i});}
        }

        // fill all cells reachable from the boundary

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int xx=current[0], yy=current[1];

            for (int i=0; i < 4; i++) {
                int testx = xx + dx[i], testy = yy + dy[i];
                if (testx >= 0 && testx < x
                        && testy >=0 && testy < y) {
                    if (grid[testx][testy] == 1) {
                        grid[testx][testy] = 0;
                        int[] n = new int[]{testx, testy};
                        queue.add(n);
                    }
                }
            }
        }

        int count = 0;
        for (int xpos = 0; xpos < x; xpos++)
            for (int ypos = 0; ypos < y; ypos++)
                count += grid[xpos][ypos];

        return count;
    }

}