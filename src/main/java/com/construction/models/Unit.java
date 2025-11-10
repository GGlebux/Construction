package com.construction.models;

import com.construction.models.enumeration.UnitStatus;
import com.construction.models.enumeration.UnitType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    private UnitType type;

    @NotNull
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private UnitStatus status;

    @Column(name = "completion_date")
    private Instant completionDate;

    @OneToMany(fetch = LAZY, mappedBy = "unit")
    @Cache(usage = READ_WRITE)
    @JsonIgnoreProperties(value = { "project", "unit" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "unit")
    @Cache(usage = READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "unit" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = { "units", "photos" }, allowSetters = true)
    private BuildingProject project;
}
