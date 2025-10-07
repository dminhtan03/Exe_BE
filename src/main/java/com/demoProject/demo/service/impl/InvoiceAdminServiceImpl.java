package com.demoProject.demo.service.impl;

import com.demoProject.demo.common.enums.BookingStatus;
import com.demoProject.demo.model.dto.response.CampingSiteDTO;
import com.demoProject.demo.model.dto.response.InvoiceDTO;
import com.demoProject.demo.model.dto.response.InvoiceDetailDTO;
import com.demoProject.demo.model.entity.Booking;
import com.demoProject.demo.model.entity.BookingDetail;
import com.demoProject.demo.model.entity.CampingSite;
import com.demoProject.demo.repository.BookingRepository;
import com.demoProject.demo.service.InvoiceAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceAdminServiceImpl implements InvoiceAdminService {

    private final BookingRepository bookingRepository;

    @Override
    public Page<InvoiceDTO> getAllInvoices(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public InvoiceDTO getInvoiceById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return mapToDTO(booking);
    }

    @Override
    public InvoiceDTO updateInvoiceStatus(String id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        try {
            booking.setStatus(BookingStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }

        Booking updated = bookingRepository.save(booking);
        return mapToDTO(updated);
    }

    @Override
    public void deleteInvoice(String id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<InvoiceDTO> searchInvoices(String bookingId,
                                           LocalDateTime start,
                                           LocalDateTime end,
                                           Pageable pageable) {
        Page<Booking> bookings;

        if (bookingId != null && start != null && end != null) {
            bookings = bookingRepository
                    .findByIdContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                            bookingId, start, end, pageable);
        } else if (bookingId != null) {
            bookings = bookingRepository.findByIdContainingIgnoreCase(bookingId, pageable);
        } else if (start != null && end != null) {
            bookings = bookingRepository.findByStartTimeBetween(start, end, pageable);
        } else {
            bookings = bookingRepository.findAll(pageable);
        }

        return bookings.map(this::mapToDTO);
    }

    // ================= Mapper Booking -> InvoiceDTO =================
    private InvoiceDTO mapToDTO(Booking booking) {
        CampingSite site = booking.getCampingSite();
        CampingSiteDTO siteDTO = new CampingSiteDTO(
                site.getId(),
                site.getName(),
                site.getDescription(),
                site.getLocation(),
                site.getLatitude(),
                site.getLongitude(),
                site.getIsActive()
        );

        return new InvoiceDTO(
                booking.getId(),
                booking.getStatus().name(),
                booking.getTotalPrice(),
                booking.getCreatedAt(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getUser().getUserInfo().getFullName(),
                booking.getUser().getUserInfo().getEmail(),
                booking.getUser().getUserInfo().getPhoneNumber(),
                siteDTO,
                booking.getDetails().stream()
                        .map(this::mapDetailToDTO)
                        .toList()
        );
    }

    private InvoiceDetailDTO mapDetailToDTO(BookingDetail detail) {
        return new InvoiceDetailDTO(
                detail.getCampingInfor().getId(),
                detail.getCampingInfor().getName(),
                detail.getCheckInDate().toString(),
                detail.getCheckOutDate().toString(),
                detail.getPrice()
        );
    }
}
