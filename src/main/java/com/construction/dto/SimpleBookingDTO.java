package com.construction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBookingDTO {
    @NotNull
    private Long clientId;

    @NotNull
    private Long unitId;

    private String note;
}
