package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CheapestFlight {

    private static class TripState {
        int node;
        int hops;
        int cost;

        TripState(int node, int hops, int cost) {
            this.node = node;
            this.hops = hops;
            this.cost = cost;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int maxEdges) {
        if (flights.length == 0 || flights[0].length == 0) return -1;
        List<int[]>[] graph = new ArrayList[n];
        for (int i=0; i<n; i++)
            graph[i] = new ArrayList<>();
        for (int[] f:flights) {
            graph[f[0]].add(new int[]{f[1],f[2]});
        }

        int maxStops = maxEdges + 1;

        int[][] best = new int[n][maxEdges+1];
        for (int i=0; i<n; i++) {
            Arrays.fill(best[i], Integer.MAX_VALUE);
        }
        best[src][0] = 0;

        final PriorityQueue<TripState> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));

        pq.offer(new TripState(src, 0,0));

        while(!pq.isEmpty()) {
            final TripState currentState = pq.poll();

            if (currentState.cost != best[currentState.node][currentState.hops]) continue;
            if (currentState.node == dst) return currentState.cost;

            if (currentState.hops == maxEdges) continue;

            for (int[] edge: graph[currentState.node]) {
                int neighbour = edge[0], price = edge[1];
                int nextEdgeCount = currentState.cost + 1;

                if (currentState.cost > Integer.MAX_VALUE - price) continue;

                int nextCost = currentState.cost + price;
                if (nextCost <best[neighbour][nextEdgeCount]) {
                    best[neighbour][nextEdgeCount] = nextCost;
                    pq.offer(new TripState(neighbour, nextEdgeCount, nextCost));
                }
            }
        }

        return -1;
    }
}
