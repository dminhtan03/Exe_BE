package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.request.RegisterPartnerRequest;
import com.demoProject.demo.model.dto.response.RegisterPartnerResponse;

public interface PartnerService {
    public RegisterPartnerResponse registerPartner(RegisterPartnerRequest request);
}
