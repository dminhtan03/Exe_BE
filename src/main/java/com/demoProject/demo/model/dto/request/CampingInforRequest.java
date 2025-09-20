package com.demoProject.demo.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CampingInforRequest {
    private String ownerId;  // ID User tạo camping
    private String name;
    private String address;
    private String description;
    private Double basePrice;       // Giá giữ chỗ
    private String thumbnail;
    private List<CampingServiceRequest> services; // Danh sách dịch vụ kèm giá
}
