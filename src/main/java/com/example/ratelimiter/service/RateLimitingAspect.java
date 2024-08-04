package com.example.ratelimiter.service;

import com.example.ratelimiter.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class RateLimitingAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private RateLimiterService rateLimiterService;

    @Before("@annotation(RateLimit)")
    public void rateLimit() throws IOException {
        String ip = httpServletRequest.getRemoteAddr();
        if(!rateLimiterService.isAllowed(ip)){
            throw new RateLimitExceededException("Too many requests from IP: " + ip);
        }
    }



}
