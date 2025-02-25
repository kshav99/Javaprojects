package com.keshavprojs.RateLimiter.controller;

import com.keshavprojs.RateLimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/resource")
    public ResponseEntity<String> accessResource(@RequestParam String userId) {
        if (rateLimiterService.isAllowed(userId)) {
            return ResponseEntity.ok("Request successful!");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded! Try again later.");
        }
    }
}
