package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RecentEventCounter {
    private final long windowMillis;

    // For each user, keep recent event timestamps in chronological order.
    // Oldest timestamp is at the front, newest timestamp is at the back.
    private final Map<String, Deque<Long>> eventsByUser = new HashMap<>();

    public RecentEventCounter(long windowMillis) {
        this.windowMillis = windowMillis;
    }

    public void record(String userId, long timestampMillis) {
        // Create the user's queue if this is their first event.
        Deque<Long> events = eventsByUser.computeIfAbsent(
                userId,
                ignored -> new ArrayDeque<>()
        );

        // Add this event as the newest timestamp.
        events.offerLast(timestampMillis);
    }

    public int countRecent(String userId, long nowMillis) {
        Deque<Long> events = eventsByUser.get(userId);

        // No recorded events for this user.
        if (events == null) {
            return 0;
        }

        // Remove timestamps that are older than the time window.
        while (!events.isEmpty() && nowMillis - events.peekFirst() >= windowMillis) {
            events.pollFirst();
        }

        return events.size();
    }
}