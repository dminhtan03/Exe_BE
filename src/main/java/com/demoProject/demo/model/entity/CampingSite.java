package com.demoProject.demo.model.entity;

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
@Table(name = "tbl_camping_site")
public class CampingSite {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private User partner; // chỉ user có role PARTNER

    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;

    @Column(name = "is_active")
    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "campingSite", cascade = CascadeType.ALL)
    private List<CampingRoom> rooms;
}
