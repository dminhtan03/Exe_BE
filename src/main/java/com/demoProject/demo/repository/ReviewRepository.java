package com.demoProject.demo.repository;
import com.demoProject.demo.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    
}
