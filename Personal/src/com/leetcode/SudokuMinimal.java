package com.leetcode;

public class SudokuMinimal {

    public void solveSudoku(char[][] board) {
        backtrack(board);
    }

    private boolean backtrack(char[][] board) {
        // find next empty cell
        for (int row = 0; row < 9; row++) {
            for (int column= 0; column< 9; column++) {
                if (board[row][column] != '.') continue;

                // try digits 1..9
                for (char ch = '1'; ch <= '9'; ch++) {
                    if (!isValid(board, row, column, ch)) continue;

                    board[row][column] = ch;
                    if (backtrack(board)) return true;
                    board[row][column] = '.';
                }

                // no digit fits here
                return false;
            }
        }

        // no empty cells left
        return true;
    }

    private boolean isValid(char[][] board, int row, int column, char ch) {
        // row + col
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == ch) return false;
            if (board[i][column] == ch) return false;
        }

        // 3x3 box
        int br = (row / 3) * 3;
        int bc = (column / 3) * 3;
        for (int i = br; i < br + 3; i++) {
            for (int j = bc; j < bc + 3; j++) {
                if (board[i][j] == ch) return false;
            }
        }

        return true;
    }
    
}
