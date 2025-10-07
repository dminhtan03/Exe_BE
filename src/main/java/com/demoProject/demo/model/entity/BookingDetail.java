package com.demoProject.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_booking_detail")
public class BookingDetail {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "campingInfor_id", nullable = false)
    private CampingInfor room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double price;
}