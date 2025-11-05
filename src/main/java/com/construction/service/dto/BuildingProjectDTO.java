package com.construction.service.dto;

import com.construction.domain.enumeration.BuildingType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.construction.domain.BuildingProject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingProjectDTO)) {
            return false;
        }

        BuildingProjectDTO buildingProjectDTO = (BuildingProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buildingProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", minPrice=" + getMinPrice() +
            ", completionDate='" + getCompletionDate() + "'" +
            "}";
    }
}
