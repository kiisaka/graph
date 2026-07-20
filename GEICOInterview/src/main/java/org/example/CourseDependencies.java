package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseDependencies {
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        int[] numPrerequisites = new int[numCourses];

        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            numPrerequisites[course]++;
        }

        //  Identify courses without prerequisites and add them to the queue

        Queue<Integer> queue = new ArrayDeque<>();

        for (int course = 0; course < numCourses; course++) {
            if (numPrerequisites[course] == 0) {
                queue.offer(course);
            }
        }

        int processed = 0;

        //  check off prerequisites as we process courses and add new courses to the queue when they have no remaining prerequisites

        while (!queue.isEmpty()) {
            int current = queue.poll();
            processed++;
            for (int next : graph.get(current)) {
                numPrerequisites[next]--;
                if (numPrerequisites[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        //  Return if all courses have been processed, indicating that it's possible to finish all courses without circular dependencies

        return processed == numCourses;

    }
}
