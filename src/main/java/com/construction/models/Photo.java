package com.construction.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cache;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "units", "photos" }, allowSetters = true)
    private BuildingProject project;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Unit unit;
}