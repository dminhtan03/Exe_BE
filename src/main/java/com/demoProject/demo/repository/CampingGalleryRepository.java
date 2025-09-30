package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampingGalleryRepository extends JpaRepository<CampingGallery, String> {
    List<CampingGallery> findByCamping_Id(String campingId);
}
