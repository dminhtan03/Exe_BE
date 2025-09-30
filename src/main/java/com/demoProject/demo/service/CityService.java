package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.CityRequest;
import com.demoProject.demo.model.dto.response.CityResponse;
import java.util.List;

public interface CityService {
    CityResponse createCity(CityRequest request);
    CityResponse updateCity(String id, CityRequest request);
    void deleteCity(String id);
    CityResponse getCityById(String id);
    List<CityResponse> getAllCities();
}
