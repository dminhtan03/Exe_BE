package com.demoProject.demo.service.impl;

import com.demoProject.demo.common.enums.BookingStatus;
import com.demoProject.demo.model.dto.request.BookingRequest;
import com.demoProject.demo.model.dto.response.BookingResponse;
import com.demoProject.demo.model.entity.*;
import com.demoProject.demo.repository.*;
import com.demoProject.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CampingInforRepository campingInforRepository;
    private final UserRepository userRepository;
    private final CampingTentRepository campingTentRepository;
    private final CampingServiceRepository campingServiceRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUserInfoEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CampingInfor room = campingInforRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setUser(user);
        booking.setCampingSite(room.getCampingSite());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(java.time.LocalDateTime.now());
        booking.setUpdatedAt(java.time.LocalDateTime.now());

        List<BookingDetail> details = new ArrayList<>();

        // Handle tent
        if (request.getCampingTentId() != null) {
            CampingTent tent = campingTentRepository.findById(request.getCampingTentId())
                    .orElseThrow(() -> new IllegalArgumentException("Tent not found"));
            BookingDetail tentDetail = new BookingDetail();
            tentDetail.setId(UUID.randomUUID().toString());
            tentDetail.setBooking(booking);
            tentDetail.setPrice(tent.getPricePerNight());
            // tentDetail.setRoom(room); // If needed
            details.add(tentDetail);
        }

        // Handle services
        if (request.getCampingServiceIds() != null) {
            for (String serviceId : request.getCampingServiceIds()) {
                CampingService service = campingServiceRepository.findById(serviceId)
                        .orElseThrow(() -> new IllegalArgumentException("Service not found"));
                BookingDetail serviceDetail = new BookingDetail();
                serviceDetail.setId(UUID.randomUUID().toString());
                serviceDetail.setBooking(booking);
                serviceDetail.setPrice(service.getPrice());
                // serviceDetail.setRoom(room); // If needed
                details.add(serviceDetail);
            }
        }

        booking.setDetails(details);

        bookingRepository.save(booking);

        return new BookingResponse(booking.getId(), "SUCCESS");
    }

    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public BookingResponse updateBooking(String bookingId, BookingRequest request) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUserInfoEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
        }

        CampingInfor room = campingInforRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        booking.setCampingSite(room.getCampingSite());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setUpdatedAt(java.time.LocalDateTime.now());

        // Remove old details and add new ones
        List<BookingDetail> details = new ArrayList<>();

        if (request.getCampingTentId() != null) {
            CampingTent tent = campingTentRepository.findById(request.getCampingTentId())
                    .orElseThrow(() -> new IllegalArgumentException("Tent not found"));
            BookingDetail tentDetail = new BookingDetail();
            tentDetail.setId(UUID.randomUUID().toString());
            tentDetail.setBooking(booking);
            tentDetail.setPrice(tent.getPricePerNight());
            details.add(tentDetail);
        }

        if (request.getCampingServiceIds() != null) {
            for (String serviceId : request.getCampingServiceIds()) {
                CampingService service = campingServiceRepository.findById(serviceId)
                        .orElseThrow(() -> new IllegalArgumentException("Service not found"));
                BookingDetail serviceDetail = new BookingDetail();
                serviceDetail.setId(UUID.randomUUID().toString());
                serviceDetail.setBooking(booking);
                serviceDetail.setPrice(service.getPrice());
                details.add(serviceDetail);
            }
        }

        booking.setDetails(details);

        bookingRepository.save(booking);

        return new BookingResponse(booking.getId(), "UPDATED");
    }

    @Override
    public void deleteBooking(String bookingId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUserInfoEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Access denied");
        }

        bookingRepository.delete(booking);
    }
}