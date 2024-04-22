package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.User;
import com.app.watch_wise_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getFullName());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
