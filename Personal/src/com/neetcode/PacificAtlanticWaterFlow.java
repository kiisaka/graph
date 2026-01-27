package com.neetcode;

import java.util.*;
import java.util.stream.Collectors;

public class PacificAtlanticWaterFlow {

    final static int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {

        // Corner cases
        //      height or width = 1

        if (heights.length == 1 || heights[0].length == 1)
            return allCells(heights);

        final int height = heights.length, width = heights[0].length;
        final boolean[][] accessibleFromPacific = new boolean[height][width];
        final boolean[][] accessibleFromAtlantic = new boolean[height][width];

        //  work on the Pacific

        for (int y=0; y < heights.length; y++) {
            traverse(heights, y, 0, accessibleFromPacific);
            traverse(heights, y, heights[0].length-1, accessibleFromAtlantic);
        }
        for (int x=0; x < heights[0].length; x++) {
            traverse(heights, 0, x, accessibleFromPacific);
            traverse(heights, heights.length-1, x, accessibleFromAtlantic);
        }

        //  Then add the intersection

        final List<List<Integer>> result = new ArrayList<>();
        for (int y=0; y < heights.length; y++)
            for (int x=0; x < heights[0].length; x++) {
                if (accessibleFromPacific[y][x] && accessibleFromAtlantic[y][x])
                    result.add(Arrays.asList(y,x));
            }
        return result;
    }

    private void traverse(int[][] heights, int y, int x, boolean[][] accessible) {
        if (accessible[y][x]) return;   //  Already touched
        accessible[y][x] = true;
        System.out.println("[" + y + "," + x + "]");
        for (int i=0; i<4; i++) {
            int newy = y + directions[i][0];
            int newx = x + directions[i][1];
            if (newx < 0 || newy < 0 || newy >= heights.length || newx >= heights[0].length) {
                continue;
            }
            if (! accessible[newy][newx] && heights[y][x] <= heights[newy][newx]) {
                traverse(heights, newy, newx, accessible);
            }

        }
    }

    private List<List<Integer>> allCells(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        for (int y=0; y<heights.length; y++) {
            for (int x = 0; x < heights[0].length; x++) {
                result.add(List.of(y, x));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] heights1 = new int[][]{{4,2,7,3,4},{7,4,6,4,7},{6,3,5,3,6}};
        int[][] heights2 = new int[][]{{2,1},{1,2}};

        final PacificAtlanticWaterFlow solution = new PacificAtlanticWaterFlow();
        solution.pacificAtlantic(heights1);
        solution.pacificAtlantic(heights2);

    }
}
