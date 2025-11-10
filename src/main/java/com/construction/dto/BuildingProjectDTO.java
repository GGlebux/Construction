package com.construction.dto;

import com.construction.models.enumeration.BuildingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * A DTO for the {@link com.construction.models.BuildingProject} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingProjectDTO {

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

    public BuildingProjectDTO(Long id) {
        this.id = id;
    }
}
