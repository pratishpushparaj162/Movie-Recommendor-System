package com.pratish.movies.dto;

import lombok.*;

/**
 * Request payload for recommendation endpoints.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecommendRequest {
    private Integer userId ;
    private Integer topN ; // optional; controller defaults if null/<=0
}

