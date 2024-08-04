package com.example.ratelimiter.service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long timeWindowMillis;
    private final ConcurrentMap<String, ConcurrentLinkedDeque<Instant>> requestLogs = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int maxRequests, long timeWindow, TimeUnit timeUnit) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeUnit.toMillis(timeWindow);
    }

    public boolean isAllowed(String ip) {
        Instant now = Instant.now();
        requestLogs.putIfAbsent(ip, new ConcurrentLinkedDeque<>());
        ConcurrentLinkedDeque<Instant> timestamps = requestLogs.get(ip);

        synchronized (timestamps) {
            // Remove outdated requests
            while (!timestamps.isEmpty() && now.minusMillis(timeWindowMillis).isAfter(timestamps.peekFirst())) {
                timestamps.pollFirst();
            }

            // Check if the number of requests is within the allowed limit
            if (timestamps.size() < maxRequests) {
                timestamps.addLast(now);
                return true;
            } else {
                return false;
            }
        }
    }
}
