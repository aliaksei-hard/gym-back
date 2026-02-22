package com.learning.gymback.security.filter;

import com.learning.gymback.security.entity.SecurityUser;
import com.learning.gymback.security.service.JwtService;
import com.learning.gymback.service.SecurityUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        Cookie[] cookies = request.getCookies();

        String jwtToken = null;
        if (cookies != null && cookies.length != 0) {
            log.info("Cookies in request found");

            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("jwt_token")) {
                    jwtToken = cookies[i].getValue();
                }
            }
        }

        // String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || context.getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwtToken = jwtToken.substring(7);
        String email = jwtService.extractSubject(jwtToken);

        if (email != null && jwtService.isTokenValid(jwtToken)) {
            SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityUser, null,
                    securityUser.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

}
