package com.leetcode;

import java.util.*;

public class CheapestFlightCorrect {

    // Dijkstra-style on expanded state: (cost, node, edgesUsed)
    // K stops => at most K+1 edges (flights).
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] f : flights) {
            graph[f[0]].add(new int[]{f[1], f[2]}); // to, price
        }

        int maxEdges = k + 1;

        // best[node][edgesUsed] = cheapest cost to reach node using exactly edgesUsed edges
        int[][] best = new int[n][maxEdges + 1];
        for (int i = 0; i < n; i++) Arrays.fill(best[i], Integer.MAX_VALUE);
        best[src][0] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.offer(new State(src, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            // Skip stale entries
            if (cur.cost != best[cur.node][cur.edgesUsed]) continue;

            // Because pq pops lowest cost first, the first time we pop dst is optimal
            // among all valid states (edgesUsed <= maxEdges).
            if (cur.node == dst) return cur.cost;

            if (cur.edgesUsed == maxEdges) continue; // cannot take more flights

            for (int[] e : graph[cur.node]) {
                int nei = e[0], price = e[1];
                int nextEdges = cur.edgesUsed + 1;

                // Guard overflow (not strictly needed with typical constraints, but safe)
                if (cur.cost > Integer.MAX_VALUE - price) continue;

                int nextCost = cur.cost + price;
                if (nextCost < best[nei][nextEdges]) {
                    best[nei][nextEdges] = nextCost;
                    pq.offer(new State(nei, nextEdges, nextCost));
                }
            }
        }

        return -1;
    }

    private static class State {
        int node;
        int edgesUsed;
        int cost;
        State(int node, int edgesUsed, int cost) {
            this.node = node;
            this.edgesUsed = edgesUsed;
            this.cost = cost;
        }
    }
}
