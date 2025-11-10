package com.construction.dto;

import com.construction.models.enumeration.BuildingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullProjectDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BuildingType type;

    @NotNull
    private String address;

    private String description;

    private BigDecimal minPrice;

    private Instant completionDate;

    private Set<String> photos = new HashSet<>();

    private Set<UnitDTO> units = new HashSet<>();
}
