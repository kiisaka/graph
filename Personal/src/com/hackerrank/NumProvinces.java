package com.hackerrank;

public class NumProvinces {
    public int findCircleNum(int[][] isConnected) {
        int provinces = 0;
        int n = isConnected.length;
        boolean[] seen = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!seen[i]) {
                provinces++;
                dfs(isConnected, seen, i);
            }
        }

        return provinces;
    }

    private void dfs(int[][] isConnected, boolean[] seen, int start) {
        seen[start] = true;
        for (int i = 0; i < isConnected.length; i++) {
            if (isConnected[start][i] == 1 && !seen[i])
                dfs(isConnected, seen, i);
        }
    }
}

