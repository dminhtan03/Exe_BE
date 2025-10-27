package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.request.UpdateBookingRequest;
import com.demoProject.demo.model.dto.response.BookingByUserIdResponse;
import com.demoProject.demo.model.dto.response.BookingResponse;
import com.demoProject.demo.model.dto.response.BookingByCampingIdResponse;
import com.demoProject.demo.model.entity.Booking;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);

    List<BookingByUserIdResponse> getBookingsByUserId(String userId);

    Page<BookingByCampingIdResponse> getBookingsByCampingId(String campingId, int page, int size);

    BookingResponse updateBooking(String bookingId, UpdateBookingRequest request);

    void cancelBooking(String bookingId);
}