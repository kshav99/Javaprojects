package com.keshavprojs.RateLimiter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class RateLimiterService {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);
    private int maxRequests = 100;
    private long windowMs = 60000;
    private Map<Integer, Queue<Long>> timeMap = new HashMap<>();
    private long totalRequests = 0;
    private long blockedRequests = 0;

    public boolean isAllowed(String userId) {
        totalRequests++;
        int userIdInt = Integer.parseInt(userId);
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowMs;

        if (!timeMap.containsKey(userIdInt)) {
            Queue<Long> queue = new LinkedList<>();
            queue.add(currentTime);
            timeMap.put(userIdInt, queue);
            logger.info("Request allowed for user {} (first request)", userId);
            return true;
        }

        Queue<Long> queue = timeMap.get(userIdInt);
        while (!queue.isEmpty() && queue.peek() < windowStart) {
            queue.poll();
        }

        if (queue.size() < maxRequests) {
            queue.add(currentTime);
            logger.info("Request allowed for user {} (queue size: {})", userId, queue.size());
            return true;
        } else {
            blockedRequests++;
            logger.info("Request blocked for user {} (limit reached: {})", userId, maxRequests);
            return false;
        }
    }

    public void updateRule(int newMaxRequests, long newWindowSeconds) {
        this.maxRequests = newMaxRequests;
        this.windowMs = newWindowSeconds * 1000;
        logger.info("Rule updated: maxRequests={}, windowSeconds={}", newMaxRequests, newWindowSeconds);
    }

    public int getMaxRequests() { return maxRequests; }
    public long getWindowMs() { return windowMs; }
    public long getTotalRequests() { return totalRequests; }
    public long getBlockedRequests() { return blockedRequests; }
}
