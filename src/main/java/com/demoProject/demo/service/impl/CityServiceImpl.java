package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.CityRequest;
import com.demoProject.demo.model.dto.response.CityResponse;
import com.demoProject.demo.model.entity.City;
import com.demoProject.demo.repository.CityRepository;
import com.demoProject.demo.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) {
        if (cityRepository.existsByName(request.getName())) {
            throw new RuntimeException("City already exists with name: " + request.getName());
        }
        City city = City.builder()
                .id(UUID.randomUUID().toString()) // tạo id mới kiểu String
                .name(request.getName())
                .build();
        cityRepository.save(city);
        return mapToResponse(city);
    }

    @Override
    public CityResponse updateCity(String id, CityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        city.setName(request.getName());
        cityRepository.save(city);
        return mapToResponse(city);
    }

    @Override
    public void deleteCity(String id) {
        cityRepository.deleteById(id);
    }

    @Override
    public CityResponse getCityById(String id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return mapToResponse(city);
    }

    @Override
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CityResponse mapToResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
