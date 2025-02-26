package com.keshavprojs.RateLimiter.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class RateLimiterService {
    private int maxRequests = 100; // Default limit
    private long windowMs = 60000; // Default window (60s in ms)
    private Map<Integer, Queue<Long>> timeMap = new HashMap<>();

    public boolean isAllowed(String userId) {
        int userIdInt = Integer.parseInt(userId);
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowMs;

        if (!timeMap.containsKey(userIdInt)) {
            Queue<Long> queue = new LinkedList<>();
            queue.add(currentTime);
            timeMap.put(userIdInt, queue);
            return true;
        }

        Queue<Long> queue = timeMap.get(userIdInt);
        while (!queue.isEmpty() && queue.peek() < windowStart) {
            queue.poll();
        }

        if (queue.size() < maxRequests) {
            queue.add(currentTime);
            return true;
        } else {
            return false;
        }
    }

    // New method to update rules from UI
    public void updateRule(int newMaxRequests, long newWindowSeconds) {
        this.maxRequests = newMaxRequests;
        this.windowMs = newWindowSeconds * 1000; // Convert seconds to ms
    }

    // Getter for UI to display current rule
    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowMs() {
        return windowMs;
    }
}