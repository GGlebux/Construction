package com.construction.domain;

import com.construction.domain.enumeration.UnitStatus;
import com.construction.domain.enumeration.UnitType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "area", precision = 21, scale = 2, nullable = false)
    private BigDecimal area;

    @NotNull
    @Column(name = "floor", nullable = false)
    private Integer floor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UnitType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UnitStatus status;

    @Column(name = "completion_date")
    private Instant completionDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project", "unit" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "unit" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "units", "photos" }, allowSetters = true)
    private BuildingProject project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public Unit location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Unit price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public Unit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getArea() {
        return this.area;
    }

    public Unit area(BigDecimal area) {
        this.setArea(area);
        return this;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public Unit floor(Integer floor) {
        this.setFloor(floor);
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public UnitType getType() {
        return this.type;
    }

    public Unit type(UnitType type) {
        this.setType(type);
        return this;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public UnitStatus getStatus() {
        return this.status;
    }

    public Unit status(UnitStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public Instant getCompletionDate() {
        return this.completionDate;
    }

    public Unit completionDate(Instant completionDate) {
        this.setCompletionDate(completionDate);
        return this;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setUnit(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setUnit(this));
        }
        this.photos = photos;
    }

    public Unit photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Unit addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setUnit(this);
        return this;
    }

    public Unit removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setUnit(null);
        return this;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setUnit(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setUnit(this));
        }
        this.bookings = bookings;
    }

    public Unit bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public Unit addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setUnit(this);
        return this;
    }

    public Unit removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setUnit(null);
        return this;
    }

    public BuildingProject getProject() {
        return this.project;
    }

    public void setProject(BuildingProject buildingProject) {
        this.project = buildingProject;
    }

    public Unit project(BuildingProject buildingProject) {
        this.setProject(buildingProject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return getId() != null && getId().equals(((Unit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", area=" + getArea() +
            ", floor=" + getFloor() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            "}";
    }
}
