package com.demoProject.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "tbl_city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id; // giữ String (UUID)

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampingInfor> campings;
}
