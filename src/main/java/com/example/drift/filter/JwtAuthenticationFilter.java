package com.example.drift.filter;

import com.example.drift.config.JwtProperties;
import com.example.drift.service.CustomUserDetailsService;
import com.example.drift.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=== JWT FILTER CALLED ===");
        System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
        log.info("=== JWT Filter: Processing request {} {}", request.getMethod(), request.getRequestURI());
        
        try {
            String jwt = parseJwt(request);
            log.info("JWT token parsed: {}", jwt != null ? "present" : "null");
            
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                log.info("JWT token valid for user: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("User authorities: {}", userDetails.getAuthorities());
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication set for user: {} with roles: {}", username, userDetails.getAuthorities());
            } else if (jwt != null) {
                log.warn("JWT token invalid");
            } else {
                log.info("No JWT token found in request");
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage(), e);
        }

        log.info("=== JWT Filter: Continuing to next filter");
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtProperties.getHeaderName());
        log.debug("Raw Authorization header: '{}'", headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtProperties.getTokenPrefix())) {
            String token = headerAuth.substring(jwtProperties.getTokenPrefix().length());
            log.debug("Raw token before cleanup: '{}'", token);
            
            // Очищаем токен от пробелов и переносов строк
            String cleanedToken = token.trim().replaceAll("\\s+", "");
            log.debug("Cleaned token: '{}'", cleanedToken);
            
            return cleanedToken;
        }

        return null;
    }
}
