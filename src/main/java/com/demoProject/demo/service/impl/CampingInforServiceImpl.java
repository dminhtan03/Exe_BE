package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.CampingInforRequest;
import com.demoProject.demo.model.dto.request.CampingServiceRequest;
import com.demoProject.demo.model.dto.response.CampingInforResponse;
import com.demoProject.demo.model.dto.response.CampingServiceResponse;
import com.demoProject.demo.model.entity.CampingInfor;
import com.demoProject.demo.model.entity.CampingService;
import com.demoProject.demo.model.entity.ServiceEntity;
import com.demoProject.demo.model.entity.Owner;
import com.demoProject.demo.repository.CampingInforRepository;
import com.demoProject.demo.repository.ServiceRepository;
import com.demoProject.demo.repository.OwnerRepository;
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
    private final OwnerRepository ownerRepository;

    @Override
    public CampingInforResponse createCamping(CampingInforRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        CampingInfor camping = CampingInfor.builder()
                .owner(owner)
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .thumbnail(request.getThumbnail())
                .bookedCount(0)
                .revenue(0.0)
                .active(false) // mặc định khi tạo mới là active
                .rate(0.0)
                .build();

        if (request.getServices() != null && !request.getServices().isEmpty()) {
            List<CampingService> campingServices = request.getServices().stream()
                    .map(this::mapToCampingService)
                    .peek(s -> s.setCamping(camping))
                    .collect(Collectors.toList());
            camping.setServices(campingServices);
        }

        campingRepository.save(camping);
        return toResponse(camping);
    }

    @Override
    public CampingInforResponse updateCamping(String id, CampingInforRequest request) {
        CampingInfor camping = campingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camping not found"));

        if (request.getOwnerId() != null) {
            Owner owner = ownerRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            camping.setOwner(owner);
        }

        camping.setName(request.getName());
        camping.setAddress(request.getAddress());
        camping.setDescription(request.getDescription());
        camping.setBasePrice(request.getBasePrice());
        camping.setThumbnail(request.getThumbnail());

        if (request.getServices() != null) {
            camping.getServices().clear();
            List<CampingService> campingServices = request.getServices().stream()
                    .map(this::mapToCampingService)
                    .peek(s -> s.setCamping(camping))
                    .collect(Collectors.toList());
            camping.getServices().addAll(campingServices);
        }

        campingRepository.save(camping);
        return toResponse(camping);
    }

    @Override
    public List<CampingInforResponse> getAllCamping() {
        return campingRepository.findAll().stream()
                .map(this::toResponse) // thêm active vào response
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
        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found: " + request.getServiceId()));

        return CampingService.builder()
                .service(service)
                .price(request.getPrice())
                .build();
    }

   private CampingInforResponse toResponse(CampingInfor camping) {
    List<CampingServiceResponse> services = camping.getServices() != null ?
            camping.getServices().stream()
                    .map(s -> CampingServiceResponse.builder()
                            .id(s.getId())
                            .serviceId(s.getService().getId())
                            .serviceName(s.getService().getName())
                            .price(s.getPrice())
                            .build())
                    .collect(Collectors.toList()) : List.of();

    return CampingInforResponse.builder()
            .id(camping.getId())
            .ownerId(camping.getOwner() != null ? camping.getOwner().getId() : null)
            .name(camping.getName())
            .address(camping.getAddress())
            .description(camping.getDescription())
            .basePrice(camping.getBasePrice())
            .thumbnail(camping.getThumbnail())
            .bookedCount(camping.getBookedCount())
            .revenue(camping.getRevenue())
            .active(camping.getActive())
            .rate(camping.getRate())  // ← Thêm dòng này
            .services(services)
            .createdAt(camping.getCreatedAt())
            .updatedAt(camping.getUpdatedAt())
            .build();
}

}
