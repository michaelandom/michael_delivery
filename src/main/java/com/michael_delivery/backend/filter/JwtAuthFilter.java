package com.michael_delivery.backend.filter;

import com.michael_delivery.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String token = extractJwtFromRequest(request);
        if (token != null && jwtUtil.validateToken(token,true)) {
            String username = jwtUtil.extractUsername(token,true);
            List<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token);
          System.out.println(authorities);
          SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, authorities)
            );
        }
        filterChain.doFilter(request, response);
    }
}
