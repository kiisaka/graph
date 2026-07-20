package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MeetingRooms {

    public int minMeetingRooms(int[][] intervals) {
        // No meetings require no rooms.
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Sort meetings by start time.
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));

        // Min-heap of end times for meetings currently using rooms.
        // The smallest end time is the room that frees up soonest.
        Queue<Integer> endTimes = new PriorityQueue<>();

        // Process each meeting in chronological order.
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // If the room with the earliest end time is free by this meeting's start,
            // remove it from the heap so we can reuse that room.
            if (!endTimes.isEmpty() && endTimes.peek() <= start) {
                endTimes.poll();
            }

            // Add this meeting's end time.
            // This represents occupying one room until 'end'.
            endTimes.offer(end);
        }

        // The heap size is the number of rooms needed at peak overlap.
        return endTimes.size();
    }
}