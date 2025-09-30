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
    private String cityId;       // ID của thành phố
    private String cityName;     // Tên của thành phố
    private String name;
    private String address;
    private String description;  // Mô tả camping
    private Double basePrice;    // Giá giữ chỗ
    private String thumbnail;    // Ảnh đại diện
    private Integer bookedCount; // Số lượt đặt
    private Double revenue;      // Doanh thu
    private List<CampingServiceResponse> services;  // Danh sách dịch vụ
    private List<CampingTentResponse> tents;       // Danh sách lều
    private List<CampingGalleryResponse> galleries; // Danh sách hình ảnh
    private Boolean active;      // Admin duyệt
    private Double rate;         // Trung bình đánh giá
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
