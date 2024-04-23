package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.Role;
import com.app.watch_wise_backend.model.User;
import com.app.watch_wise_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

    public User registerUser(String username, String email, String password, String fullName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public Map<String, String> loginUser(String username, String password, HttpServletResponse response) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return authService.generateTokensAndSetCookies(user, response);
        } else {
            return Collections.singletonMap("error", "Invalid credentials");
        }
    }
}
