package com.construction.domain;

import com.construction.domain.enumeration.BuildingType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cache;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * A BuildingProject.
 */
@Entity
@Table(name = "building_project")
@Cache(usage = READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BuildingProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    @Cache(usage = READ_WRITE)
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Set<Unit> units = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "project")
    @Cache(usage = READ_WRITE)
    @JsonIgnoreProperties(value = { "project", "unit" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();
}
