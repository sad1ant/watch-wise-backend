package com.app.watch_wise_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilterParams {
    private String genre;
    private String startYear;
    private String endYear;
    private String artist;
    private String ageRating;
    private String rating;
    private String country;
}
