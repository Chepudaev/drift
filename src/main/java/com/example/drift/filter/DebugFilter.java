package com.example.drift.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class DebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=== DEBUG FILTER CALLED - VERSION 2.0 ===");
        System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("Query String: " + request.getQueryString());
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
        System.out.println("Content-Type: " + request.getContentType());
        
        log.info("=== DEBUG FILTER: {} {} ===", request.getMethod(), request.getRequestURI());
        log.info("Authorization: {}", request.getHeader("Authorization"));
        
        try {
            filterChain.doFilter(request, response);
            System.out.println("=== DEBUG FILTER COMPLETED SUCCESSFULLY ===");
        } catch (Exception e) {
            System.out.println("=== DEBUG FILTER ERROR: " + e.getMessage() + " ===");
            throw e;
        }
    }
}
