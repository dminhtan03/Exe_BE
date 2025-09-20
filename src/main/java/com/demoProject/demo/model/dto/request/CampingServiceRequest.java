package com.demoProject.demo.model.dto.request;

import lombok.Data;

@Data
public class CampingServiceRequest {
    private String serviceId; // ID của Service đã có sẵn
    private Double price;     // Giá dịch vụ theo camping
}
