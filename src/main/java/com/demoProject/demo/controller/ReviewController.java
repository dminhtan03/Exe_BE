// src/main/java/com/demoProject/demo/controller/ReviewController.java
package com.demoProject.demo.controller;

import com.demoProject.demo.model.dto.request.ReviewRequest;
import com.demoProject.demo.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok("Review submitted successfully");
    }
}