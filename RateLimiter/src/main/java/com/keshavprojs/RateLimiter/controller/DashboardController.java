package com.keshavprojs.RateLimiter.controller;

import com.keshavprojs.RateLimiter.service.RateLimiterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final RateLimiterService rateLimiterService;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public DashboardController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("maxRequests", rateLimiterService.getMaxRequests());
        model.addAttribute("windowSeconds", rateLimiterService.getWindowMs() / 1000);
        model.addAttribute("totalRequests", rateLimiterService.getTotalRequests());
        model.addAttribute("blockedRequests", rateLimiterService.getBlockedRequests());
        return "dashboard";
    }

    @PostMapping("/update-rule")
    public String updateRule(@RequestParam("maxRequests") int maxRequests,
                             @RequestParam("windowSeconds") long windowSeconds) {
        rateLimiterService.updateRule(maxRequests, windowSeconds);
        logger.info("Rule update requested via UI: maxRequests={}, windowSeconds={}", maxRequests, windowSeconds);
        return "redirect:/dashboard";
    }

    @PostMapping("/reset-all") // New endpoint for Reset All button
    public String resetAll() {
        rateLimiterService.resetAll();
        logger.info("Reset all requested via UI.");
        return "redirect:/dashboard"; // Redirect back to dashboard to refresh stats
    }


    @GetMapping("/stats-stream")
    public SseEmitter streamStats() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        executor.execute(() -> {
            try {
                while (true) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("{\"totalRequests\":" + rateLimiterService.getTotalRequests() +
                                    ",\"blockedRequests\":" + rateLimiterService.getBlockedRequests() +
                                    ",\"maxRequests\":" + rateLimiterService.getMaxRequests() +
                                    ",\"windowSeconds\":" + (rateLimiterService.getWindowMs() / 1000) + "}");
                    emitter.send(event);
                    Thread.sleep(1000); // 1s - Reduced to 1 second for faster updates
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}
