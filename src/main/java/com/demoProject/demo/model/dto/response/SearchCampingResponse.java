package com.demoProject.demo.model.dto.response;

import com.demoProject.demo.common.enums.RoomStatus;
import lombok.Data;

@Data
public class SearchCampingResponse {
    private String roomId;
    private String roomName;
    private String description;
    private Integer capacity;
    private Double pricePerNight;
    private RoomStatus status;
}