package com.demoProject.demo.controller.user;

import com.demoProject.demo.model.entity.CampingSite;
import com.demoProject.demo.service.CampingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camping-sites")
@RequiredArgsConstructor
public class CampingSiteController {
    private final CampingService campingservice;

    @GetMapping
    public ResponseEntity<List<CampingSite>> getAllCampingSites() {
        return ResponseEntity.ok(campingservice.getAllCampingSites());
    }
}
