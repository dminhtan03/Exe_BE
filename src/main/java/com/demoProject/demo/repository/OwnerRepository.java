package com.demoProject.demo.repository;

import com.demoProject.demo.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
