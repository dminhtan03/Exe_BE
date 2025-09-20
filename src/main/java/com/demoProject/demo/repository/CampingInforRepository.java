package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampingInforRepository extends JpaRepository<CampingInfor, String> {

    // Tìm tất cả camping theo địa chỉ (address)
    List<CampingInfor> findByAddressContainingIgnoreCase(String address);

    // Tìm camping theo tên
    List<CampingInfor> findByNameContainingIgnoreCase(String name);
}
