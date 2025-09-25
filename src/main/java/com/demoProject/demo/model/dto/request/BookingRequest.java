package com.demoProject.demo.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRequest {
    @NotNull
    private String userId;

    @NotNull
    private String campingSiteId;

    @NotNull
    private String roomId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Double totalPrice;
}