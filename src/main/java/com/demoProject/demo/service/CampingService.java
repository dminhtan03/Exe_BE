package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.SearchCampingRequest;
import com.demoProject.demo.model.dto.response.SearchCampingResponse;

import java.util.List;

public interface CampingService {
    List<SearchCampingResponse> searchCamping(SearchCampingRequest request);
}