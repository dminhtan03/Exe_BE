package com.demoProject.demo.model.dto.response;

import lombok.Setter;

import java.util.UUID;

@Setter
public class UserResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String department;

    private String email;

    private String gender;

    private boolean isReset;
}
