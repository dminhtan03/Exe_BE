package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampingInforRepository extends JpaRepository<CampingInfor, String> {

    // Tìm tất cả camping theo địa chỉ (address)
    List<CampingInfor> findByAddressContainingIgnoreCase(String address);

    // Tìm camping theo tên
    List<CampingInfor> findByNameContainingIgnoreCase(String name);

    // =================== Thêm cho Active ===================

    // Lấy tất cả camping đã được duyệt (active = true)
    List<CampingInfor> findByActiveTrue();

    // Lấy tất cả camping chưa được duyệt (active = false)
    List<CampingInfor> findByActiveFalse();

    // Lấy tất cả camping của 1 owner theo trạng thái duyệt
    List<CampingInfor> findByOwnerIdAndActiveTrue(String ownerId);
    List<CampingInfor> findByOwnerIdAndActiveFalse(String ownerId);
}
