package com.example.ratelimiter.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {
    private final SlidingWindowRateLimiter rateLimiter;

    public RateLimiterService() {
        this.rateLimiter = new SlidingWindowRateLimiter(10, 1, TimeUnit.MINUTES); // 10 requests per minute
    }

    public boolean isAllowed(String ip) {
        return rateLimiter.isAllowed(ip);
    }

}
