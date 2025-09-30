package com.demoProject.demo.repository;
import com.demoProject.demo.model.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;


@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
}
