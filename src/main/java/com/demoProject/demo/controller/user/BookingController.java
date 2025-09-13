package com.demoProject.demo.controller.user;

import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.response.BookingResponse;
import com.demoProject.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Ensure the user is authenticated
    public ResponseEntity<BookingResponse> createBooking(@Validated @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }
}