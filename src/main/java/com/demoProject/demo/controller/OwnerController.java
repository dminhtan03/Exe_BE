package com.demoProject.demo.controller;

import com.demoProject.demo.model.dto.request.OwnerRequest;
import com.demoProject.demo.model.dto.response.OwnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService service;

    @PostMapping
    public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerRequest request) {
        return ResponseEntity.ok(service.createOwner(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable String id,
                                                     @RequestBody OwnerRequest request) {
        return ResponseEntity.ok(service.updateOwner(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable String id) {
        return ResponseEntity.ok(service.getOwnerById(id));
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        return ResponseEntity.ok(service.getAllOwners());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable String id) {
        service.deleteOwner(id);
        return ResponseEntity.ok("Owner deleted successfully");
    }
}
