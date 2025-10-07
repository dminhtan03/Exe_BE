package com.demoProject.demo.controller;

import com.demoProject.demo.model.dto.request.RegisterPartnerRequest;
import com.demoProject.demo.model.dto.response.RegisterPartnerResponse;
import com.demoProject.demo.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/partner")
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping("/register")
    public RegisterPartnerResponse registerPartner(@RequestBody RegisterPartnerRequest request) {
        return partnerService.registerPartner(request);
    }
}