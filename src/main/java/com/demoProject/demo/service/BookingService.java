package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
}