package com.construction.criteria;

import com.construction.models.enumeration.UnitStatus;
import com.construction.models.enumeration.UnitType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.construction.models.Unit} entity. This class is used
 * in {@link com.construction.web.rest.UnitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /units?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnitCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UnitType
     */
    public static class UnitTypeFilter extends Filter<UnitType> {

        public UnitTypeFilter() {}

        public UnitTypeFilter(UnitTypeFilter filter) {
            super(filter);
        }

        @Override
        public UnitTypeFilter copy() {
            return new UnitTypeFilter(this);
        }
    }

    /**
     * Class for filtering UnitStatus
     */
    public static class UnitStatusFilter extends Filter<UnitStatus> {

        public UnitStatusFilter() {}

        public UnitStatusFilter(UnitStatusFilter filter) {
            super(filter);
        }

        @Override
        public UnitStatusFilter copy() {
            return new UnitStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter location;

    private BigDecimalFilter price;

    private StringFilter description;

    private BigDecimalFilter area;

    private IntegerFilter floor;

    private UnitTypeFilter type;

    private UnitStatusFilter status;

    private InstantFilter completionDate;

    private LongFilter photosId;

    private LongFilter bookingsId;

    private LongFilter projectId;

    private Boolean distinct;

    public UnitCriteria() {}

    public UnitCriteria(UnitCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.location = other.optionalLocation().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(BigDecimalFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.area = other.optionalArea().map(BigDecimalFilter::copy).orElse(null);
        this.floor = other.optionalFloor().map(IntegerFilter::copy).orElse(null);
        this.type = other.optionalType().map(UnitTypeFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(UnitStatusFilter::copy).orElse(null);
        this.completionDate = other.optionalCompletionDate().map(InstantFilter::copy).orElse(null);
        this.photosId = other.optionalPhotosId().map(LongFilter::copy).orElse(null);
        this.bookingsId = other.optionalBookingsId().map(LongFilter::copy).orElse(null);
        this.projectId = other.optionalProjectId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UnitCriteria copy() {
        return new UnitCriteria(this);
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

    public StringFilter getLocation() {
        return location;
    }

    public Optional<StringFilter> optionalLocation() {
        return Optional.ofNullable(location);
    }

    public StringFilter location() {
        if (location == null) {
            setLocation(new StringFilter());
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public Optional<BigDecimalFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public BigDecimalFilter price() {
        if (price == null) {
            setPrice(new BigDecimalFilter());
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
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

    public BigDecimalFilter getArea() {
        return area;
    }

    public Optional<BigDecimalFilter> optionalArea() {
        return Optional.ofNullable(area);
    }

    public BigDecimalFilter area() {
        if (area == null) {
            setArea(new BigDecimalFilter());
        }
        return area;
    }

    public void setArea(BigDecimalFilter area) {
        this.area = area;
    }

    public IntegerFilter getFloor() {
        return floor;
    }

    public Optional<IntegerFilter> optionalFloor() {
        return Optional.ofNullable(floor);
    }

    public IntegerFilter floor() {
        if (floor == null) {
            setFloor(new IntegerFilter());
        }
        return floor;
    }

    public void setFloor(IntegerFilter floor) {
        this.floor = floor;
    }

    public UnitTypeFilter getType() {
        return type;
    }

    public Optional<UnitTypeFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public UnitTypeFilter type() {
        if (type == null) {
            setType(new UnitTypeFilter());
        }
        return type;
    }

    public void setType(UnitTypeFilter type) {
        this.type = type;
    }

    public UnitStatusFilter getStatus() {
        return status;
    }

    public Optional<UnitStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public UnitStatusFilter status() {
        if (status == null) {
            setStatus(new UnitStatusFilter());
        }
        return status;
    }

    public void setStatus(UnitStatusFilter status) {
        this.status = status;
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

    public LongFilter getBookingsId() {
        return bookingsId;
    }

    public Optional<LongFilter> optionalBookingsId() {
        return Optional.ofNullable(bookingsId);
    }

    public LongFilter bookingsId() {
        if (bookingsId == null) {
            setBookingsId(new LongFilter());
        }
        return bookingsId;
    }

    public void setBookingsId(LongFilter bookingsId) {
        this.bookingsId = bookingsId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public Optional<LongFilter> optionalProjectId() {
        return Optional.ofNullable(projectId);
    }

    public LongFilter projectId() {
        if (projectId == null) {
            setProjectId(new LongFilter());
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
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
        final UnitCriteria that = (UnitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(location, that.location) &&
            Objects.equals(price, that.price) &&
            Objects.equals(description, that.description) &&
            Objects.equals(area, that.area) &&
            Objects.equals(floor, that.floor) &&
            Objects.equals(type, that.type) &&
            Objects.equals(status, that.status) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(photosId, that.photosId) &&
            Objects.equals(bookingsId, that.bookingsId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            location,
            price,
            description,
            area,
            floor,
            type,
            status,
            completionDate,
            photosId,
            bookingsId,
            projectId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLocation().map(f -> "location=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalArea().map(f -> "area=" + f + ", ").orElse("") +
            optionalFloor().map(f -> "floor=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalCompletionDate().map(f -> "completionDate=" + f + ", ").orElse("") +
            optionalPhotosId().map(f -> "photosId=" + f + ", ").orElse("") +
            optionalBookingsId().map(f -> "bookingsId=" + f + ", ").orElse("") +
            optionalProjectId().map(f -> "projectId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
