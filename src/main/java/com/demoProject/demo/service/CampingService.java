package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.SearchCampingRequest;
import com.demoProject.demo.model.dto.response.CampingInforResponse;
import com.demoProject.demo.model.dto.response.CampingRoomListResponse;
import com.demoProject.demo.model.dto.response.SearchCampingResponse;
import com.demoProject.demo.model.entity.CampingSite;
import com.demoProject.demo.model.dto.response.CampingSiteSimpleResponse;

import java.util.List;

public interface CampingService {
    List<SearchCampingResponse> searchCamping(SearchCampingRequest request);
    List<CampingSiteSimpleResponse> getAllCampingSiteLocations();
    List<CampingRoomListResponse> getAllCampingRooms();

    List<CampingInforResponse> getCampingRoomsBySiteId(String campingSiteId);
}