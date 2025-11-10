package com.construction.dto;

import com.construction.models.enumeration.UnitStatus;
import com.construction.models.enumeration.UnitType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A DTO for the {@link com.construction.models.Unit} entity.
 */
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

    public UnitDTO(Long id) {
        this.id = id;
    }
}
