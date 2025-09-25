package com.demoProject.demo.repository;
import com.demoProject.demo.model.entity.CampingSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingSiteRepository extends JpaRepository<CampingSite, String> {
   
}
