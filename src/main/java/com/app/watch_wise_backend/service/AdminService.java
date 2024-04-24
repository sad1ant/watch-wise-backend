package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.User;
import com.app.watch_wise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public List<Map<String, String>> getUserList() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(u -> {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("id", u.getId().toString());
                    userInfo.put("username", u.getUsername());
                    userInfo.put("email", u.getEmail());
                    userInfo.put("fullName", u.getFullName());
                    userInfo.put("role", u.getRole().toString());
                    return userInfo;
                })
                .collect(Collectors.toList());
    }
}
