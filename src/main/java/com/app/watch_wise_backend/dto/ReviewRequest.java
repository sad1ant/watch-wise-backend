package com.app.watch_wise_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private String description;
    private Long movieId;
    private Long seriesId;
}
