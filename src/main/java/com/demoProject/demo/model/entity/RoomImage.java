package com.demoProject.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_room_image")
public class RoomImage {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private CampingRoom room;

    private String imageUrl;
}