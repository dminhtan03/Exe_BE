package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.OwnerRequest;
import com.demoProject.demo.model.dto.response.OwnerResponse;

import java.util.List;

public interface OwnerService {
    OwnerResponse createOwner(OwnerRequest request);
    OwnerResponse updateOwner(String id, OwnerRequest request);
    OwnerResponse getOwnerById(String id);
    List<OwnerResponse> getAllOwners();
    void deleteOwner(String id);
}
