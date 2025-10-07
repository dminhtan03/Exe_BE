package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampingRoomRepository extends JpaRepository<CampingInfor, String> {

   // Use CampingInfor and its relationships for availability search
    @Query("SELECT c FROM CampingInfor c " +
           "JOIN c.campingSite s " +
           "WHERE s.location = :destination " +
           "AND c.active = true " +
           "AND NOT EXISTS (" +
           "   SELECT b FROM Booking b " +
           "   JOIN b.details d " +
           "   WHERE d.campingInfor.id = c.id " +
           "   AND (b.startTime < :endTime AND b.endTime > :startTime)" +
           ")")
    List<CampingInfor> findAvailableCampingInfors(String destination, LocalDateTime startTime, LocalDateTime endTime);
}