package com.app.watch_wise_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private int releaseYear;
    private double rating;
    private String image;
}
