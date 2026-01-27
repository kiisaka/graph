package com.leetcode;

public class Sudoku {

    class Solution {

        //  Instead of 2d arrays, we can use an array of int and use big masks
        private boolean[][] row = new boolean[9][9];
        private boolean[][] col = new boolean[9][9];
        private boolean[][] box = new boolean[9][9];

        public void solveSudoku(char[][] board) {

            if (board.length != 9) return;
            if (board[0].length != 9) return;

            // initialize bookkeeping
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    char ch = board[r][c];
                    if (ch != '.') {
                        int d = ch - '1';
                        row[r][d] = true;
                        col[c][d] = true;
                        box[boxIndex(r, c)][d] = true;
                    }
                }
            }

            backtrack(board);
        }

        private boolean backtrack(char[][] board) {
            // find next empty cell
            int r = -1, c = -1;
            boolean found = false;
            for (int i = 0; i < 9 && !found; i++) {
                for (int j = 0; j < 9 && !found; j++) {
                    if (board[i][j] == '.') {
                        r = i;
                        c = j;
                        found = true;
                    }
                }
            }

            // no empties => solved
            if (!found) return true;

            int b = boxIndex(r, c);
            for (int d = 0; d < 9; d++) {
                if (row[r][d] || col[c][d] || box[b][d]) continue;

                // place
                board[r][c] = (char) ('1' + d);
                row[r][d] = col[c][d] = box[b][d] = true;

                if (backtrack(board)) return true;

                // undo
                board[r][c] = '.';
                row[r][d] = col[c][d] = box[b][d] = false;
            }

            return false;
        }

        private int boxIndex(int r, int c) {
            return (r / 3) * 3 + (c / 3);
        }
    }
}
