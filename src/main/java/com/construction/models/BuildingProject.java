package com.construction.models;

import com.construction.models.enumeration.BuildingType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


/**
 * A BuildingProject.
 */
@Entity
@Table(name = "building_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingProject {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(STRING)
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

    @OneToMany(fetch = LAZY, mappedBy = "project")
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Set<Unit> units = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "project")
    @JsonIgnoreProperties(value = { "project", "unit" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();
}
