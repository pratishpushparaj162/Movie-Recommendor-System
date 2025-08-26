package com.pratish.movies.dto;

import lombok.*;

/**
 * Recommendation result returned by the API.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecommendationDto {
    private Integer movieId;
    private String movieTitle;
    private Double score;
}
