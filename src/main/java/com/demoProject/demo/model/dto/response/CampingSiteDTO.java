package com.demoProject.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampingSiteDTO {
    private String id;
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private Boolean isActive;
}
