package com.construction.service.criteria;

import com.construction.domain.enumeration.BuildingType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.construction.domain.BuildingProject} entity. This class is used
 * in {@link com.construction.web.rest.BuildingProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /building-projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuildingProjectCriteria implements Serializable, Criteria {

    /**
     * Class for filtering BuildingType
     */
    public static class BuildingTypeFilter extends Filter<BuildingType> {

        public BuildingTypeFilter() {}

        public BuildingTypeFilter(BuildingTypeFilter filter) {
            super(filter);
        }

        @Override
        public BuildingTypeFilter copy() {
            return new BuildingTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BuildingTypeFilter type;

    private StringFilter address;

    private StringFilter description;

    private BigDecimalFilter minPrice;

    private InstantFilter completionDate;

    private LongFilter unitsId;

    private LongFilter photosId;

    private Boolean distinct;

    public BuildingProjectCriteria() {}

    public BuildingProjectCriteria(BuildingProjectCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(BuildingTypeFilter::copy).orElse(null);
        this.address = other.optionalAddress().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.minPrice = other.optionalMinPrice().map(BigDecimalFilter::copy).orElse(null);
        this.completionDate = other.optionalCompletionDate().map(InstantFilter::copy).orElse(null);
        this.unitsId = other.optionalUnitsId().map(LongFilter::copy).orElse(null);
        this.photosId = other.optionalPhotosId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BuildingProjectCriteria copy() {
        return new BuildingProjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BuildingTypeFilter getType() {
        return type;
    }

    public Optional<BuildingTypeFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public BuildingTypeFilter type() {
        if (type == null) {
            setType(new BuildingTypeFilter());
        }
        return type;
    }

    public void setType(BuildingTypeFilter type) {
        this.type = type;
    }

    public StringFilter getAddress() {
        return address;
    }

    public Optional<StringFilter> optionalAddress() {
        return Optional.ofNullable(address);
    }

    public StringFilter address() {
        if (address == null) {
            setAddress(new StringFilter());
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BigDecimalFilter getMinPrice() {
        return minPrice;
    }

    public Optional<BigDecimalFilter> optionalMinPrice() {
        return Optional.ofNullable(minPrice);
    }

    public BigDecimalFilter minPrice() {
        if (minPrice == null) {
            setMinPrice(new BigDecimalFilter());
        }
        return minPrice;
    }

    public void setMinPrice(BigDecimalFilter minPrice) {
        this.minPrice = minPrice;
    }

    public InstantFilter getCompletionDate() {
        return completionDate;
    }

    public Optional<InstantFilter> optionalCompletionDate() {
        return Optional.ofNullable(completionDate);
    }

    public InstantFilter completionDate() {
        if (completionDate == null) {
            setCompletionDate(new InstantFilter());
        }
        return completionDate;
    }

    public void setCompletionDate(InstantFilter completionDate) {
        this.completionDate = completionDate;
    }

    public LongFilter getUnitsId() {
        return unitsId;
    }

    public Optional<LongFilter> optionalUnitsId() {
        return Optional.ofNullable(unitsId);
    }

    public LongFilter unitsId() {
        if (unitsId == null) {
            setUnitsId(new LongFilter());
        }
        return unitsId;
    }

    public void setUnitsId(LongFilter unitsId) {
        this.unitsId = unitsId;
    }

    public LongFilter getPhotosId() {
        return photosId;
    }

    public Optional<LongFilter> optionalPhotosId() {
        return Optional.ofNullable(photosId);
    }

    public LongFilter photosId() {
        if (photosId == null) {
            setPhotosId(new LongFilter());
        }
        return photosId;
    }

    public void setPhotosId(LongFilter photosId) {
        this.photosId = photosId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BuildingProjectCriteria that = (BuildingProjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(address, that.address) &&
            Objects.equals(description, that.description) &&
            Objects.equals(minPrice, that.minPrice) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(unitsId, that.unitsId) &&
            Objects.equals(photosId, that.photosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, address, description, minPrice, completionDate, unitsId, photosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingProjectCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalAddress().map(f -> "address=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalMinPrice().map(f -> "minPrice=" + f + ", ").orElse("") +
            optionalCompletionDate().map(f -> "completionDate=" + f + ", ").orElse("") +
            optionalUnitsId().map(f -> "unitsId=" + f + ", ").orElse("") +
            optionalPhotosId().map(f -> "photosId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
