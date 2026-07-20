package org.example;

public class MaxPathValue {
    public int maxPathValue(int[][] grid) {
        // Handle empty input defensively.
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        // dp[row][col] stores the maximum value collectable
        // from the top-left cell to cell (row, col).
        int[][] dp = new int[rows][cols];

        // Starting cell.
        dp[0][0] = grid[0][0];

        // First row:
        // We can only get here by moving right from the previous cell.
        for (int col = 1; col < cols; col++) {
            dp[0][col] = dp[0][col - 1] + grid[0][col];
        }

        // First column:
        // We can only get here by moving down from the previous cell.
        for (int row = 1; row < rows; row++) {
            dp[row][0] = dp[row - 1][0] + grid[row][0];
        }

        // Fill the rest of the table.
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                // Choose the better of the two possible previous cells:
                // from above or from the left.
                int bestPrevious = Math.max(dp[row - 1][col], dp[row][col - 1]);

                dp[row][col] = bestPrevious + grid[row][col];
            }
        }

        // Bottom-right cell contains the best total value.
        return dp[rows - 1][cols - 1];
    }
}