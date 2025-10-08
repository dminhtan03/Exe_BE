// src/main/java/com/demoProject/demo/service/impl/ReviewServiceImpl.java
package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.ReviewRequest;
import com.demoProject.demo.model.entity.*;
import com.demoProject.demo.repository.*;
import com.demoProject.demo.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final CampingInforRepository campingInforRepository;
    private final UserRepository userRepository;

    @Override
    public void createReview(ReviewRequest request) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUserInfoEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
        }

        if (!booking.getStatus().name().equals("COMPLETED")) {
            throw new IllegalArgumentException("Booking not completed");
        }

        // Check if already reviewed
        if (reviewRepository.findAll().stream().anyMatch(r -> r.getBooking().getId().equals(booking.getId()))) {
            throw new IllegalArgumentException("Already reviewed");
        }

        CampingInfor campingInfor = campingInforRepository.findById(request.getCampingInforId())
                .orElseThrow(() -> new IllegalArgumentException("CampingInfor not found"));

        Review review = new Review();
        review.setId(UUID.randomUUID().toString());
        review.setUser(user);
        review.setBooking(booking);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        // Update average rating
        var reviews = reviewRepository.findAll().stream()
                .filter(r -> r.getBooking().getCampingSite().getId().equals(campingInfor.getCampingSite().getId()))
                .toList();
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        campingInfor.setRate(avg);
        campingInforRepository.save(campingInfor);
    }
}