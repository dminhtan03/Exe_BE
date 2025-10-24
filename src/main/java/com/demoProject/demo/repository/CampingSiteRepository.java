package com.demoProject.demo.repository;
import com.demoProject.demo.model.entity.CampingSite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampingSiteRepository extends JpaRepository<CampingSite, String> {
   List<CampingSite> findByPartner_Id(String partnerId);
}
