package com.keshavprojs.RateLimiter.service;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    public boolean isAllowed(String userId) {
        // TODO: Implement rate-limiting logic using Deque + Counter Map
        return true; // Placeholder, allow all requests for now
    }
}