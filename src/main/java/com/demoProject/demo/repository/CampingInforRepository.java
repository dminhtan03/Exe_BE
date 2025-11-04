package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampingInforRepository extends JpaRepository<CampingInfor, String> {

    @Query("""
        SELECT c FROM CampingInfor c
        JOIN c.campingSite s
        WHERE s.location = :destination
          AND c.active = true
          AND NOT EXISTS (
              SELECT b FROM Booking b
              JOIN b.details d
              WHERE d.room.id = c.id
              AND (b.startTime < :endTime AND b.endTime > :startTime)
          )
    """)
    List<CampingInfor> findAvailableCampingInfors(String destination,LocalDateTime startTime,LocalDateTime endTime);

    // Derived query remains (keeps compatibility)
    List<CampingInfor> findByNameContainingIgnoreCase(String name);

    // New: fetch-join campingSite to avoid lazy loading errors when mapping campingSite fields
    @Query("SELECT c FROM CampingInfor c LEFT JOIN FETCH c.campingSite s WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CampingInfor> findByNameContainingIgnoreCaseFetchSite(String name);
}
