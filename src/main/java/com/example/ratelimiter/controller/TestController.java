package com.example.ratelimiter.controller;

import com.example.ratelimiter.service.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RateLimit
    @GetMapping("/api/test")
    public String testEndpoint() {
        return "Request successful!";
    }
}