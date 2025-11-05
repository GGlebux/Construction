package com.construction.domain;

import com.construction.domain.enumeration.BuildingType;
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
 * A BuildingProject.
 */
@Entity
@Table(name = "building_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuildingProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BuildingType type;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "min_price", precision = 21, scale = 2)
    private BigDecimal minPrice;

    @Column(name = "completion_date")
    private Instant completionDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Set<Unit> units = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project", "unit" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BuildingProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BuildingProject name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingType getType() {
        return this.type;
    }

    public BuildingProject type(BuildingType type) {
        this.setType(type);
        return this;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public String getAddress() {
        return this.address;
    }

    public BuildingProject address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public BuildingProject description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    public BuildingProject minPrice(BigDecimal minPrice) {
        this.setMinPrice(minPrice);
        return this;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Instant getCompletionDate() {
        return this.completionDate;
    }

    public BuildingProject completionDate(Instant completionDate) {
        this.setCompletionDate(completionDate);
        return this;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public Set<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Unit> units) {
        if (this.units != null) {
            this.units.forEach(i -> i.setProject(null));
        }
        if (units != null) {
            units.forEach(i -> i.setProject(this));
        }
        this.units = units;
    }

    public BuildingProject units(Set<Unit> units) {
        this.setUnits(units);
        return this;
    }

    public BuildingProject addUnits(Unit unit) {
        this.units.add(unit);
        unit.setProject(this);
        return this;
    }

    public BuildingProject removeUnits(Unit unit) {
        this.units.remove(unit);
        unit.setProject(null);
        return this;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setProject(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setProject(this));
        }
        this.photos = photos;
    }

    public BuildingProject photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public BuildingProject addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setProject(this);
        return this;
    }

    public BuildingProject removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setProject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingProject)) {
            return false;
        }
        return getId() != null && getId().equals(((BuildingProject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingProject{" +
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
