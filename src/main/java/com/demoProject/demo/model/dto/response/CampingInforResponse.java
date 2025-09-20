package com.demoProject.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CampingInforResponse {
    private String id;
    private String ownerId;
    private String name;
    private String address;
    private String description;
    private Double basePrice;
    private String thumbnail;
    private Integer bookedCount;
    private Double revenue;
    private List<CampingServiceResponse> services;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
