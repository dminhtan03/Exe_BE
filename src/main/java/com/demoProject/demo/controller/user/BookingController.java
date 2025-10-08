package com.demoProject.demo.controller.user;

import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.response.BookingResponse;
import com.demoProject.demo.model.entity.Booking;
import com.demoProject.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Ensure the user is authenticated
    public ResponseEntity<BookingResponse> createBooking(@Validated @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable String bookingId,
            @Validated @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.updateBooking(bookingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok().build();
    }
}