package com.construction.dto;

import com.construction.models.enumeration.UnitStatus;
import com.construction.models.enumeration.UnitType;
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
public class FullUnitDTO {
    private Long id;

    private String location;

    @NotNull
    private BigDecimal price;

    private String description;

    @NotNull
    private BigDecimal area;

    @NotNull
    private Integer floor;

    @NotNull
    private UnitType type;

    @NotNull
    private UnitStatus status;

    private Instant completionDate;

    @NotNull
    private BuildingProjectDTO project;

    private Set<String> photos = new HashSet<>();
}
