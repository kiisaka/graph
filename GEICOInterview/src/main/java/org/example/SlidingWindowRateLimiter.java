package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long windowMillis;

    // For each user, store timestamps of their recent requests.
    // The deque is ordered from oldest request to newest request.
    private final Map<String, Deque<Long>> userRequests = new HashMap<>();

    public SlidingWindowRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public boolean allowRequest(String userId, long nowMillis) {
        // Get this user's timestamp queue.
        // If the user has no prior requests, create an empty queue.
        Deque<Long> requests = userRequests.computeIfAbsent(
                userId,
                ignored -> new ArrayDeque<>()
        );

        // Remove requests that are outside the sliding window.
        // After this loop, the deque contains only requests made within
        // the last windowMillis milliseconds.
        while (!requests.isEmpty() && nowMillis - requests.peekFirst() >= windowMillis) {
            requests.pollFirst();
        }

        // If fewer than maxRequests remain in the window, this request is allowed.
        // Record its timestamp because it now counts against the limit.
        if (requests.size() < maxRequests) {
            requests.offerLast(nowMillis);
            return true;
        }

        // Otherwise, the user has already made too many requests
        // within the sliding window.
        return false;
    }
}