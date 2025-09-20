package com.demoProject.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tbl_camping_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampingService {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camping_id", nullable = false)
    private CampingInfor camping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service; // <--- sửa ở đây

    @Column(nullable = false)
    private Double price; // Giá dịch vụ theo camping

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
