package com.demoProject.demo.service.impl;

import com.demoProject.demo.common.enums.RoomStatus;
import com.demoProject.demo.model.dto.request.SearchCampingRequest;
import com.demoProject.demo.model.dto.response.CampingRoomListResponse;
import com.demoProject.demo.model.dto.response.SearchCampingResponse;
import com.demoProject.demo.model.entity.CampingRoom;
import com.demoProject.demo.model.entity.CampingSite;
import com.demoProject.demo.repository.CampingRoomRepository;
import com.demoProject.demo.repository.CampingSiteRepository;
import com.demoProject.demo.service.CampingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.demoProject.demo.model.dto.response.CampingSiteSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampingServiceImpl implements CampingService {

    private final CampingRoomRepository campingRoomRepository;
    private final CampingSiteRepository campingSiteRepository;
    @Override
    public List<SearchCampingResponse> searchCamping(SearchCampingRequest request) {
        List<CampingRoom> rooms = campingRoomRepository.findAvailableRooms(
                request.getDestination(),
                request.getStartTime(),
                request.getEndTime()
        );

        return rooms.stream()
                .map(room -> {
                    SearchCampingResponse response = new SearchCampingResponse();
                    response.setRoomId(room.getId());
                    response.setRoomName(room.getName());
                    response.setDescription(room.getDescription());
                    response.setCapacity(room.getCapacity());
                    response.setPricePerNight(room.getPricePerNight());
                    response.setStatus(room.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CampingSiteSimpleResponse> getAllCampingSiteLocations() {
        return campingSiteRepository.findAll().stream().map(site -> {
            CampingSiteSimpleResponse dto = new CampingSiteSimpleResponse();
            dto.setId(site.getId());
            dto.setLocation(site.getLocation());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CampingRoomListResponse> getAllCampingRooms() {
        return campingRoomRepository.findAll().stream().map(room -> {
            CampingRoomListResponse dto = new CampingRoomListResponse();
            dto.setRoomId(room.getId());
            dto.setRoomName(room.getName());
            dto.setDescription(room.getDescription());
            dto.setCapacity(room.getCapacity());
            dto.setPricePerNight(room.getPricePerNight());
            dto.setSiteName(room.getCampingSite().getName());
            dto.setLocation(room.getCampingSite().getLocation());
            // Add this line to map image URLs
            dto.setImageUrls(room.getImages() != null
                    ? room.getImages().stream().map(img -> img.getImageUrl()).toList()
                    : List.of());
            return dto;
        }).collect(Collectors.toList());
    }
}