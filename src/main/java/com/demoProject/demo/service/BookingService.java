package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.response.BookingResponse;
import com.demoProject.demo.model.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);

    List<Booking> getBookingsByUserId(String userId);

    BookingResponse updateBooking(String bookingId, BookingRequest request);

    void deleteBooking(String bookingId);
}