package com.app.watch_wise_backend.middleware;

import com.app.watch_wise_backend.model.user.Role;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.UserRepository;
import com.app.watch_wise_backend.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AdminMiddleware extends OncePerRequestFilter {
    private final AuthService authService;
    private final UserRepository userRepository;

    public AdminMiddleware(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Unauthorized: User is not authenticated\"}");
                return;
            }

            String accessToken = authHeader.substring(7);
            if (accessToken.isEmpty()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Unauthorized: User is not authenticated\"}");
                return;
            }

            Claims claims = authService.validateAccessToken(accessToken);
            if (claims == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Unauthorized: User is not authenticated\"}");
                return;
            }

            String username = (String) claims.get("username");
            User user = userRepository.findByUsername(username);

            if (user == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"User not found\"}");
                return;
            }

            if (!Role.ADMIN.equals(user.getRole())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Forbidden: User is not an admin\"}");
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: User is not authenticated\"}");
        }
    }
}
