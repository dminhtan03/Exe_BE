package com.demoProject.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_camping_infor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampingInfor {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    // Tham chiếu tới Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(nullable = false, length = 155)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double basePrice;

    @Column(length = 500)
    private String thumbnail;

    @Column(nullable = false)
    private Integer bookedCount = 0;

    @Column(nullable = false)
    private Double revenue = 0.0;

    @OneToMany(mappedBy = "camping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampingService> services;

    @Column(nullable = false)
    private Boolean active = false; // Trường để admin duyệt

    // Thêm trường rate
    @Column(nullable = false)
    private Double rate = 0.0; // Trung bình đánh giá từ người dùng, mặc định 0

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
