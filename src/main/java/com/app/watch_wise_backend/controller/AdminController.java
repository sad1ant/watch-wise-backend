package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUserList() {
        List<Map<String, String>> userList = adminService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/add-content")
    public ResponseEntity<?> addContent(@RequestBody Map<String, Object> request) {
        String contentType = (String) request.get("contentType");
        Object content = request.get("content");

        if (contentType != null && content != null) {
            if ("movie".equals(contentType)) {
                Movie movie = new ObjectMapper().convertValue(content, Movie.class);
                Map<String, String> response = adminService.addContent(movie);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if ("series".equals(contentType)) {
                Series series = new ObjectMapper().convertValue(content, Series.class);
                Map<String, String> response = adminService.addContent(series);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "Invalid content type"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Content type and content must be provided"), HttpStatus.BAD_REQUEST);
        }
    }
}
