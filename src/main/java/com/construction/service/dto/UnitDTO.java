package com.construction.service.dto;

import com.construction.domain.enumeration.UnitStatus;
import com.construction.domain.enumeration.UnitType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.construction.domain.Unit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public BuildingProjectDTO getProject() {
        return project;
    }

    public void setProject(BuildingProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitDTO)) {
            return false;
        }

        UnitDTO unitDTO = (UnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, unitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitDTO{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", area=" + getArea() +
            ", floor=" + getFloor() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
