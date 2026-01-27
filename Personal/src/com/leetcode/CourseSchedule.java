package com.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build adjacency list: b -> a
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());

        int[] requirements = new int[numCourses];

        for (int[] p : prerequisites) {
            int a = p[0], b = p[1];
            adj.get(b).add(a);
            requirements[a]++;
        }

        //  Perform a BFS

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (requirements[i] == 0) q.add(i);
        }

        int taken = 0;
        while (!q.isEmpty()) {
            int course = q.poll();     // removes from queue
            taken++;

            for (int next : adj.get(course)) {
                requirements[next]--;
                if (requirements[next] == 0) q.add(next);
            }
        }

        return taken == numCourses;
    }

    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        int m = prerequisites.length;

        int[] outCount = new int[n];
        int[] indeg = new int[n];

        for (int k = 0; k < m; k++) {
            int a = prerequisites[k][0];
            int b = prerequisites[k][1];
            outCount[b]++;
            indeg[a]++;
        }

        int[][] adj = new int[n][];
        for (int i = 0; i < n; i++) adj[i] = new int[outCount[i]];

        int[] fill = new int[n];
        for (int k = 0; k < m; k++) {
            int a = prerequisites[k][0];
            int b = prerequisites[k][1];
            adj[b][fill[b]++] = a;
        }

        int[] q = new int[n];
        int head = 0, tail = 0;
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) q[tail++] = i;
        }

        int taken = 0;
        while (head < tail) {
            int course = q[head++];
            taken++;

            for (int nxt : adj[course]) {
                if (--indeg[nxt] == 0) q[tail++] = nxt;
            }
        }

        return taken == n;
    }
}
