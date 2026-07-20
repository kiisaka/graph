package org.example;

public class RowContaingBallsAndEmptySpaces {
    public boolean canRearrangeBalls(String row) {
        int balls = 0;
        int emptySpaces = 0;

        // Count balls and empty spaces.
        for (char c : row.toCharArray()) {
            if (c == 'B') {
                balls++;
            } else if (c == '.') {
                emptySpaces++;
            }
        }

        // To separate N balls, we need at least N - 1 empty spaces.
        // Example:
        // B . B . B requires 2 empty spaces for 3 balls.
        return emptySpaces >= balls - 1;
    }

    public String rearrangeBalls(String row) {
        int balls = 0;
        int emptySpaces = 0;

        // Count balls and empty spaces.
        for (char c : row.toCharArray()) {
            if (c == 'B') {
                balls++;
            } else if (c == '.') {
                emptySpaces++;
            }
        }

        // If there are not enough empty spaces to separate the balls,
        // no valid arrangement exists.
        if (emptySpaces < balls - 1) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        // Place balls separated by one empty space.
        // This consumes balls - 1 empty spaces.
        for (int i = 0; i < balls; i++) {
            result.append('B');

            if (i < balls - 1) {
                result.append('.');
                emptySpaces--;
            }
        }

        // Add any remaining empty spaces at the end.
        while (emptySpaces > 0) {
            result.append('.');
            emptySpaces--;
        }

        return result.toString();
    }
}