package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.User;
import com.app.watch_wise_backend.repository.UserRepository;
import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getFullName());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        Map<String, String> tokens = userService.loginUser(request.getUsername(), request.getPassword());
        if (tokens != null) {
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
