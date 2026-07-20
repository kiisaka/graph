package org.example;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter {
    private static class Window {
        // Start time of the current rate-limit window for this user.
        long windowStartMillis;

        // Number of requests seen in the current window.
        int requestCount;

        Window(long windowStartMillis, int requestCount) {
            this.windowStartMillis = windowStartMillis;
            this.requestCount = requestCount;
        }
    }

    private final int maxRequests;
    private final long windowMillis;

    // One window-tracking object per user.
    private final Map<String, Window> userWindows = new HashMap<>();

    public FixedWindowRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public boolean allowRequest(String userId, long nowMillis) {
        Window window = userWindows.get(userId);

        // If this is the user's first request, or their previous window has expired,
        // start a new window and allow this request as the first request in it.
        if (window == null || nowMillis - window.windowStartMillis >= windowMillis) {
            userWindows.put(userId, new Window(nowMillis, 1));
            return true;
        }

        // The request falls within the current window.
        // Allow it only if the user has not yet reached the limit.
        if (window.requestCount < maxRequests) {
            window.requestCount++;
            return true;
        }

        // The user has already used all allowed requests in this window.
        return false;
    }
}