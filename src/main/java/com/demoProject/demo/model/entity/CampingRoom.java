package com.demoProject.demo.model.entity;

import com.demoProject.demo.common.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_camping_room")
public class CampingRoom {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "camping_site_id", nullable = false)
    private CampingSite campingSite;

    private String name;
    private String description;
    private Integer capacity;
    private Double pricePerNight;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomImage> images;

    @ManyToMany
    @JoinTable(
            name = "tbl_room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;
}