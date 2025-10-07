package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.*;
import com.demoProject.demo.model.dto.response.*;
import com.demoProject.demo.model.entity.*;
import com.demoProject.demo.repository.*;
import com.demoProject.demo.service.CampingInforService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampingInforServiceImpl implements CampingInforService {

    private final CampingInforRepository campingRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final CampingSiteRepository campingSiteRepository;

    @Override
    public CampingInforResponse createCamping(CampingInforRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CampingSite campingSite = null;
        if (request.getCampingSiteId() != null) {
            campingSite = campingSiteRepository.findById(request.getCampingSiteId())
                    .orElseThrow(() -> new RuntimeException("Camping site not found"));
        }

        CampingInfor camping = CampingInfor.builder()
                .owner(user)
                .campingSite(campingSite)
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .thumbnail(request.getThumbnail())
                .bookedCount(0)
                .revenue(0.0)
                .active(false)
                .rate(0.0)
                .build();

        // Services
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            List<CampingService> campingServices = request.getServices().stream()
                    .map(this::mapToCampingService)
                    .peek(s -> s.setCamping(camping))
                    .collect(Collectors.toList());
            camping.setServices(campingServices);
        }

        // Tents
        if (request.getTents() != null && !request.getTents().isEmpty()) {
            List<CampingTent> campingTents = request.getTents().stream()
                    .map(this::mapToCampingTent)
                    .peek(t -> t.setCamping(camping))
                    .collect(Collectors.toList());
            camping.setTents(campingTents);
        }

        // Galleries
        if (request.getGalleries() != null && !request.getGalleries().isEmpty()) {
            List<CampingGallery> campingGalleries = request.getGalleries().stream()
                    .map(this::mapToCampingGallery)
                    .peek(g -> g.setCamping(camping))
                    .collect(Collectors.toList());
            camping.setGalleries(campingGalleries);
        }

        campingRepository.save(camping);
        return toResponse(camping);
    }

    @Override
    public CampingInforResponse updateCamping(String id, CampingInforRequest request) {
        CampingInfor camping = campingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camping not found"));

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            camping.setOwner(user);
        }

        if (request.getCampingSiteId() != null) {
            CampingSite campingSite = campingSiteRepository.findById(request.getCampingSiteId())
                    .orElseThrow(() -> new RuntimeException("Camping site not found"));
            camping.setCampingSite(campingSite);
        }

        camping.setName(request.getName());
        camping.setAddress(request.getAddress());
        camping.setDescription(request.getDescription());
        camping.setBasePrice(request.getBasePrice());
        camping.setThumbnail(request.getThumbnail());

        // Update Services
        if (request.getServices() != null) {
            camping.getServices().clear();
            List<CampingService> campingServices = request.getServices().stream()
                    .map(this::mapToCampingService)
                    .peek(s -> s.setCamping(camping))
                    .collect(Collectors.toList());
            camping.getServices().addAll(campingServices);
        }

        // Update Tents
        if (request.getTents() != null) {
            camping.getTents().clear();
            List<CampingTent> campingTents = request.getTents().stream()
                    .map(this::mapToCampingTent)
                    .peek(t -> t.setCamping(camping))
                    .collect(Collectors.toList());
            camping.getTents().addAll(campingTents);
        }

        // Update Galleries
        if (request.getGalleries() != null) {
            camping.getGalleries().clear();
            List<CampingGallery> campingGalleries = request.getGalleries().stream()
                    .map(this::mapToCampingGallery)
                    .peek(g -> g.setCamping(camping))
                    .collect(Collectors.toList());
            camping.getGalleries().addAll(campingGalleries);
        }

        campingRepository.save(camping);
        return toResponse(camping);
    }

    @Override
    public List<CampingInforResponse> getAllCamping() {
        return campingRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampingInforResponse getCampingById(String id) {
        CampingInfor camping = campingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camping not found"));
        return toResponse(camping);
    }

    @Override
    public void deleteCamping(String id) {
        campingRepository.deleteById(id);
    }

    // ---------------- Private helpers ----------------

    private CampingService mapToCampingService(CampingServiceRequest request) {
        ServiceEntity service = null;
        String customName = null;

        if (request.getServiceId() != null) {
            service = serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found: " + request.getServiceId()));
        } else if (request.getCustomName() != null) {
            customName = request.getCustomName();
        }

        return CampingService.builder()
                .service(service)
                .customName(customName)
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();
    }

    private CampingTent mapToCampingTent(CampingTentRequest request) {
        return CampingTent.builder()
                .tentName(request.getTentName())
                .capacity(request.getCapacity())
                .pricePerNight(request.getPricePerNight())
                .quantity(request.getQuantity())
                .thumbnail(request.getThumbnail())
                .build();
    }

    private CampingGallery mapToCampingGallery(CampingGalleryRequest request) {
        return CampingGallery.builder()
                .imageUrl(request.getImageUrl())
                .build();
    }

    private CampingInforResponse toResponse(CampingInfor camping) {
        List<CampingServiceResponse> services = camping.getServices() != null ?
                camping.getServices().stream()
                        .map(s -> CampingServiceResponse.builder()
                                .id(s.getId())
                                .serviceId(s.getService() != null ? s.getService().getId() : null)
                                .serviceName(s.getService() != null ? s.getService().getName() : s.getCustomName())
                                .price(s.getPrice())
                                .build())
                        .collect(Collectors.toList()) : List.of();

        List<CampingTentResponse> tents = camping.getTents() != null ?
                camping.getTents().stream()
                        .map(t -> CampingTentResponse.builder()
                                .id(t.getId())
                                .campingId(camping.getId())
                                .tentName(t.getTentName())
                                .capacity(t.getCapacity())
                                .pricePerNight(t.getPricePerNight())
                                .quantity(t.getQuantity())
                                .thumbnail(t.getThumbnail())
                                .build())
                        .collect(Collectors.toList()) : List.of();

        List<CampingGalleryResponse> galleries = camping.getGalleries() != null ?
                camping.getGalleries().stream()
                        .map(g -> CampingGalleryResponse.builder()
                                .id(g.getId())
                                .campingId(camping.getId())
                                .imageUrl(g.getImageUrl())
                                .build())
                        .collect(Collectors.toList()) : List.of();

        return CampingInforResponse.builder()
                .id(camping.getId())
                .userId(camping.getOwner() != null ? camping.getOwner().getId() : null)
                .campingSiteId(camping.getCampingSite() != null ? camping.getCampingSite().getId() : null)
                .campingSiteName(camping.getCampingSite() != null ? camping.getCampingSite().getName() : null)
                .name(camping.getName())
                .address(camping.getAddress())
                .description(camping.getDescription())
                .basePrice(camping.getBasePrice())
                .thumbnail(camping.getThumbnail())
                .bookedCount(camping.getBookedCount())
                .revenue(camping.getRevenue())
                .active(camping.getActive())
                .rate(camping.getRate())
                .services(services)
                .tents(tents)
                .galleries(galleries)
                .createdAt(camping.getCreatedAt())
                .updatedAt(camping.getUpdatedAt())
                .build();
    }
}