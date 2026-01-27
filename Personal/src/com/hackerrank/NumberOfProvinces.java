package com.hackerrank;

class NumberOfProvinces {
    public int findCircleNum(int[][] isConnected) {
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];

        for (int city=0; city < isConnected.length; city++) {
            if (!visited[city]) {
                count++;
                visited[city] = true;
                visitNeighbours(isConnected, city, visited);
            }
        }

        return count;
    }

    private void visitNeighbours(int[][] connections, int city, boolean[] visited) {
        for (int neighbour = city+1; city < connections[city].length; neighbour++) {
            if (connections[city][neighbour] == 1) {
                visited[neighbour] = true;
                visitNeighbours(connections, neighbour, visited);
            }
        }
    }

    public static void main(String[] args) {

    }
}