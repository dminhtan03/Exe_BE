package com.demoProject.demo.model.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CampingSiteResponse {
    private String id;
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> images;
}
