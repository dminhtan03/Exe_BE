package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.SearchCampingRequest;
import com.demoProject.demo.model.dto.response.CampingRoomListResponse;
import com.demoProject.demo.model.dto.response.SearchCampingResponse;
import com.demoProject.demo.model.entity.CampingInfor;
import com.demoProject.demo.model.entity.CampingTent;
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
        List<CampingInfor> campingInfors = campingRoomRepository.findAvailableCampingInfors(
                request.getDestination(),
                request.getStartTime(),
                request.getEndTime()
        );

        return campingInfors.stream().map(campingInfor -> {
            SearchCampingResponse response = new SearchCampingResponse();
            response.setRoomId(campingInfor.getId());
            response.setRoomName(campingInfor.getName());
            response.setDescription(campingInfor.getDescription());
            // Get first tent info if available
            if (campingInfor.getTents() != null && !campingInfor.getTents().isEmpty()) {
                CampingTent tent = campingInfor.getTents().get(0);
                response.setCapacity(tent.getCapacity());
                response.setPricePerNight(tent.getPricePerNight());
                response.setCampingTent(tent);
            }
            response.setRate(campingInfor.getRate());
            response.setActive(campingInfor.getActive());
            return response;
        }).collect(Collectors.toList());
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

    // Maps CampingInfor to CampingRoomListResponse using related entities
    @Override
    public List<CampingRoomListResponse> getAllCampingRooms() {
        return campingRoomRepository.findAll().stream().map(room -> {
            CampingRoomListResponse dto = new CampingRoomListResponse();
            dto.setRoomId(room.getId());
            dto.setRoomName(room.getName());
            dto.setDescription(room.getDescription());

            // Get first tent info if available
            if (room.getTents() != null && !room.getTents().isEmpty()) {
                dto.setCapacity(room.getTents().get(0).getCapacity());
                dto.setPricePerNight(room.getTents().get(0).getPricePerNight());
            } else {
                dto.setCapacity(null);
                dto.setPricePerNight(null);
            }

            dto.setSiteName(room.getCampingSite().getName());
            dto.setLocation(room.getCampingSite().getLocation());

            // Map image URLs from galleries
            dto.setImageUrls(room.getGalleries() != null
                    ? room.getGalleries().stream().map(gallery -> gallery.getImageUrl()).toList()
                    : List.of());

            return dto;
        }).collect(Collectors.toList());
    }
}