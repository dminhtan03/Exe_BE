package com.demoProject.demo.controller;

import com.demoProject.demo.model.dto.request.CampingInforRequest;
import com.demoProject.demo.model.dto.response.CampingInforResponse;
import com.demoProject.demo.service.CampingInforService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camping")
@RequiredArgsConstructor
public class CampingInforController {

    private final CampingInforService service;

    // Tạo camping mới kèm danh sách dịch vụ
    @PostMapping
    public ResponseEntity<CampingInforResponse> createCamping(@RequestBody CampingInforRequest request) {
        return ResponseEntity.ok(service.createCamping(request));
    }

    // Cập nhật thông tin camping và dịch vụ kèm giá
    @PutMapping("/{id}")
    public ResponseEntity<CampingInforResponse> updateCamping(
            @PathVariable String id,
            @RequestBody CampingInforRequest request
    ) {
        return ResponseEntity.ok(service.updateCamping(id, request));
    }

    // Lấy tất cả camping
    @GetMapping
    public ResponseEntity<List<CampingInforResponse>> getAllCamping() {
        return ResponseEntity.ok(service.getAllCamping());
    }

    // Lấy camping theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CampingInforResponse> getCampingById(@PathVariable String id) {
        return ResponseEntity.ok(service.getCampingById(id));
    }

    // Xóa camping theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCamping(@PathVariable String id) {
        service.deleteCamping(id);
        return ResponseEntity.ok("Camping deleted successfully");
    }
}
