// src/main/java/com/demoProject/demo/service/impl/PartnerServiceImpl.java
package com.demoProject.demo.service.impl;

import com.demoProject.demo.common.exception.CustomException;
import com.demoProject.demo.common.payload.ResponseCode;
import com.demoProject.demo.model.dto.request.RegisterPartnerRequest;
import com.demoProject.demo.model.dto.response.RegisterPartnerResponse;
import com.demoProject.demo.model.entity.*;
import com.demoProject.demo.repository.*;
import com.demoProject.demo.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final CampingSiteRepository campingSiteRepository;
    private final CampingInforRepository campingInforRepository;
    private final CampingGalleryRepository campingGalleryRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public RegisterPartnerResponse registerPartner(RegisterPartnerRequest request) {

        if (userInfoRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }

        // Create UserInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setFirstName(request.getFirstName());
        userInfo.setLastName(request.getLastName());
        userInfo.setPhoneNumber(request.getPhoneNumber());
        userInfo.setAddress(request.getAddress_partner());
        userInfo.setEmail(request.getEmail());
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfoRepository.save(userInfo);

        // Create User
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserInfo(userInfo);
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setApproveStatus("PENDING");
        user.setRoles(Set.of(
                roleRepository.findRoleByName("PARTNER")
                        .orElseThrow(() -> new RuntimeException("Role PARTNER not found"))
        ));
        userRepository.save(user);

        // Create CampingSite
        CampingSite campingSite = new CampingSite();
        campingSite.setId(UUID.randomUUID().toString());
        campingSite.setPartner(user);
        campingSite.setName(request.getName_camping());
        campingSite.setDescription(request.getDescription_camping());
        campingSite.setLocation(request.getAddress_camping());
        campingSite.setCreatedAt(LocalDateTime.now());
        campingSite.setIsActive(true);
        campingSiteRepository.save(campingSite);

        // Create CampingInfor (at least one, minimal info)
        CampingInfor campingInfor = CampingInfor.builder()
                .id(UUID.randomUUID().toString())
                .owner(user)
                .campingSite(campingSite)
                .name(request.getName_camping())
                .address(request.getAddress_camping())
                .description(request.getDescription_camping())
                .basePrice(0.0)
                .bookedCount(0)
                .capacity(0)
                .revenue(0.0)
                .rate(0.0)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
        campingInforRepository.save(campingInfor); // <-- Save before using

        // Save images to CampingGallery (link to CampingInfor)
        List<CampingGallery> galleries = request.getImageUrls().stream()
                .map(url -> CampingGallery.builder()
                        .id(UUID.randomUUID().toString())
                        .camping(campingInfor)
                        .imageUrl(url)
                        .build())
                .collect(Collectors.toList());
        campingGalleryRepository.saveAll(galleries);

        // Build response
        RegisterPartnerResponse response = new RegisterPartnerResponse();
        response.setFirstName(request.getFirstName());
        response.setLastName(request.getLastName());
        response.setPhoneNumber(request.getPhoneNumber());
        response.setAddress_partner(request.getAddress_partner());
        response.setAddress_camping(request.getAddress_camping());
        response.setName_camping(request.getName_camping());
        response.setDescription_camping(request.getDescription_camping());
        response.setEmail(request.getEmail());
        response.setImageUrls(request.getImageUrls());
        return response;
    }
}