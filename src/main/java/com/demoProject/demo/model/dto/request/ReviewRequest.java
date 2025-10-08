package com.demoProject.demo.model.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private String campingInforId;
    private String bookingId;
    private int rating; // e.g. 1-5
    private String comment;
}