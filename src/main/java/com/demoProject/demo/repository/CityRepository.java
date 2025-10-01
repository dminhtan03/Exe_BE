package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    boolean existsByName(String name);
}
