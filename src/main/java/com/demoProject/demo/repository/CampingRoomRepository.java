package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.CampingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampingRoomRepository extends JpaRepository<CampingRoom, String> {

    @Query("SELECT r FROM CampingRoom r " +
           "JOIN r.campingSite s " +
           "WHERE s.location = :destination " +
           "AND r.status = 'AVAILABLE' " +
           "AND NOT EXISTS (" +
           "   SELECT b FROM Booking b " +
           "   JOIN b.details d " +
           "   WHERE d.room.id = r.id " +
           "   AND (b.startTime < :endTime AND b.endTime > :startTime)" +
           ")")
    List<CampingRoom> findAvailableRooms(String destination, LocalDateTime startTime, LocalDateTime endTime);
}