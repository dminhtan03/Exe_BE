package com.demoProject.demo.service.impl;

    import com.demoProject.demo.common.enums.BookingStatus;
    import com.demoProject.demo.model.dto.request.BookingRequest;
    import com.demoProject.demo.model.dto.response.BookingResponse;
    import com.demoProject.demo.model.entity.Booking;
    import com.demoProject.demo.model.entity.CampingInfor;
    import com.demoProject.demo.model.entity.User;
    import com.demoProject.demo.repository.BookingRepository;
    import com.demoProject.demo.repository.CampingRoomRepository;
    import com.demoProject.demo.repository.UserRepository;
    import com.demoProject.demo.service.BookingService;
    import lombok.AllArgsConstructor;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Service;

    import java.util.UUID;

    @AllArgsConstructor
    @Service
    public class BookingServiceImpl implements BookingService {

        private final BookingRepository bookingRepository;
        private final CampingRoomRepository campingRoomRepository;
        private final UserRepository userRepository;

        @Override
        public BookingResponse createBooking(BookingRequest request) {
            // Get the logged-in user
            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user = userRepository.findByUserInfoEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Check if the room exists
            CampingInfor room = campingRoomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Room not found"));

            // Create the Booking object
            Booking booking = new Booking();
            booking.setId(UUID.randomUUID().toString());
            booking.setUser(user);
            booking.setCampingSite(room.getCampingSite());
            booking.setStartTime(request.getStartTime());
            booking.setEndTime(request.getEndTime());
            booking.setTotalPrice(request.getTotalPrice());
            booking.setStatus(BookingStatus.CONFIRMED); // Set default status

            // Save to the database
            bookingRepository.save(booking);

            // Return the response
            return new BookingResponse(booking.getId(), "SUCCESS");
        }
    }