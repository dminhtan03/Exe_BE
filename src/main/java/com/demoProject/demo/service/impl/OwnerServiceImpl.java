package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.OwnerRequest;
import com.demoProject.demo.model.dto.response.OwnerResponse;
import com.demoProject.demo.model.entity.Owner;
import com.demoProject.demo.repository.OwnerRepository;
import com.demoProject.demo.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OwnerResponse createOwner(OwnerRequest request) {
        Owner owner = Owner.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .build();
        repository.save(owner);
        return toResponse(owner);
    }

    @Override
    public OwnerResponse updateOwner(String id, OwnerRequest request) {
        Owner owner = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        owner.setUsername(request.getUsername());
        owner.setEmail(request.getEmail());
        owner.setPhone(request.getPhone());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            owner.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        repository.save(owner);
        return toResponse(owner);
    }

    @Override
    public OwnerResponse getOwnerById(String id) {
        Owner owner = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return toResponse(owner);
    }

    @Override
    public List<OwnerResponse> getAllOwners() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOwner(String id) {
        repository.deleteById(id);
    }

    private OwnerResponse toResponse(Owner owner) {
        return OwnerResponse.builder()
                .id(owner.getId())
                .username(owner.getUsername())
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .build();
    }
}
