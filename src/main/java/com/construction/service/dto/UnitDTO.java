package com.construction.service.dto;

import com.construction.domain.enumeration.UnitStatus;
import com.construction.domain.enumeration.UnitType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.construction.domain.Unit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO implements Serializable {

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

    private Set<String> photos;
}
