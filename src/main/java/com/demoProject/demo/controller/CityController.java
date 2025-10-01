package com.demoProject.demo.controller;

import com.demoProject.demo.model.dto.request.CityRequest;
import com.demoProject.demo.model.dto.response.CityResponse;
import com.demoProject.demo.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> create(@RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.createCity(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> update(@PathVariable String id, @RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.updateCity(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAll() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}
