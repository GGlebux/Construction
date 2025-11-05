package com.construction.service.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.construction.domain.Photo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private BuildingProjectDTO project;

    private UnitDTO unit;
}
