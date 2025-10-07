
        package com.demoProject.demo.service.impl;

        import com.demoProject.demo.model.dto.request.RegisterPartnerRequest;
        import com.demoProject.demo.model.dto.response.RegisterPartnerResponse;
        import com.demoProject.demo.model.entity.*;
        import com.demoProject.demo.repository.*;
        import com.demoProject.demo.service.PartnerService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.time.LocalDateTime;
        import java.util.UUID;
        import java.util.stream.Collectors;

        @Service
        public class PartnerServiceImpl implements PartnerService {

            @Autowired
            private UserRepository userRepository;
            @Autowired
            private UserInfoRepository userInfoRepository;
            @Autowired
            private CampingSiteRepository campingSiteRepository;
            @Autowired
            private CampingGalleryRepository campingGalleryRepository;

            public RegisterPartnerResponse registerPartner(RegisterPartnerRequest request) {
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
                user.setEnabled(true);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);

                // Create CampingSite
                CampingSite campingSite = new CampingSite();
                campingSite.setId(UUID.randomUUID().toString());
                campingSite.setPartner(user);
                campingSite.setName(request.getName_camping());
                campingSite.setDescription(request.getDescription_camping());
                campingSite.setLocation(request.getAddress_camping());
                campingSite.setCreatedAt(LocalDateTime.now());
                campingSite.setUpdatedAt(LocalDateTime.now());
                campingSiteRepository.save(campingSite);

                // Save CampingGallery images
                if (request.getImageUrls() != null) {
                    for (String url : request.getImageUrls()) {
                        CampingGallery gallery = CampingGallery.builder()
                                .id(UUID.randomUUID().toString())
                                .camping(null) // Set camping if needed
                                .imageUrl(url)
                                .build();
                        campingGalleryRepository.save(gallery);
                    }
                }

                // Prepare response
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