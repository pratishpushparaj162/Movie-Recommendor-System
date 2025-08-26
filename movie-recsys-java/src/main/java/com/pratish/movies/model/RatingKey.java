package com.pratish.movies.controller;

import com.pratish.movies.dto.RecommendRequest;
import com.pratish.movies.dto.RecommendationDto;
import com.pratish.movies.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for collaborative filtering recommendations.
 */
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Generate recommendations using User-based Collaborative Filtering.
     * POST /api/recommendations/user-based
     * Body: {"userId":1,"topN":5}
     */
    @PostMapping("/user-based")
    public ResponseEntity<List<RecommendationDto>> userBased(@RequestBody RecommendRequest request) {
        int userId = request.getUserId();
        int topN = (request.getTopN() == null || request.getTopN() <= 0) ? 5 : request.getTopN();
        return ResponseEntity.ok(recommendationService.recommendUserBased(userId, topN));
    }

    /**
     * Generate recommendations using Item-based Collaborative Filtering.
     * POST /api/recommendations/item-based
     * Body: {"userId":1,"topN":5}
     */
    @PostMapping("/item-based")
    public ResponseEntity<List<RecommendationDto>> itemBased(@RequestBody RecommendRequest request) {
        int userId = request.getUserId();
        int topN = (request.getTopN() == null || request.getTopN() <= 0) ? 5 : request.getTopN();
        return ResponseEntity.ok(recommendationService.recommendItemBased(userId, topN));
    }
}
