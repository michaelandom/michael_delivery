package com.michael_delivery.backend.exception;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String errorMessage;
        int statusCode;

        if (authException.getMessage().contains("Invalid or expired JWT token")) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED; // 401 Unauthorized
            errorMessage = "Authentication failed: Invalid or expired JWT token.";
        } else {
            statusCode = HttpServletResponse.SC_FORBIDDEN; // 403 Forbidden
            errorMessage = "Access Denied: You do not have the necessary authority to access this resource.";
        }

        response.setStatus(statusCode);
        response.setContentType("application/json");

        response.getWriter().write("{\"status\": " + statusCode + ", " +
                "\"error\": \"" + (statusCode == HttpServletResponse.SC_UNAUTHORIZED ? "Unauthorized" : "Forbidden") + "\", " +
                "\"message\": \"" + errorMessage + "\", " +
                "\"path\": \"" + request.getRequestURI() + "\"}");

    }
}



