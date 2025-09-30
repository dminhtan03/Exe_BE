package com.demoProject.demo.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CampingInforRequest {
    private String ownerId;  // ID User tạo camping
    private String cityId;   // ← Thêm ID city
    private String name;
    private String address;
    private String description;
    private Double basePrice;       // Giá giữ chỗ
    private String thumbnail;
    private Boolean active;         // Admin duyệt
    private List<CampingServiceRequest> services; // Danh sách dịch vụ kèm giá
    private List<CampingTentRequest> tents;       // ← Thêm field tents
    private List<CampingGalleryRequest> galleries; // ← Thêm field galleries
}
