package com.construction.service.dto;

import com.construction.domain.enumeration.BuildingType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.construction.domain.BuildingProject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingProjectDTO implements Serializable {

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

    private Set<String> photos;

    private Set<UnitDTO> units;
}
