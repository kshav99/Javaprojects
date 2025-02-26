package com.keshavprojs.RateLimiter.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class RateLimiterService {
    public static final int MAX_REQUESTS = 100;
    // Single map to store user ID to queue of timestamps
    public static Map<Integer, Queue<Long>> timeMap = new HashMap<>();

    public boolean isAllowed(String userId) {
        int userIdInt = Integer.parseInt(userId);
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - 60000; // 60-second window (adjust as needed)

        // Case 1: New user
        if (!timeMap.containsKey(userIdInt)) {
            Queue<Long> queue = new LinkedList<>();
            queue.add(currentTime);
            timeMap.put(userIdInt, queue);
            return true; // First request is always allowed
        }

        // Case 2: Existing user
        Queue<Long> queue = timeMap.get(userIdInt);

        // Remove timestamps outside the time window
        while (!queue.isEmpty() && queue.peek() < windowStart) {
            queue.poll();
        }

        // Check if we can allow the new request
        if (queue.size() < MAX_REQUESTS) {
            queue.add(currentTime);
            return true; // Request allowed
        } else {
            return false; // Request blocked (limit reached)
        }
    }
}
