package com.construction.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JsonIgnoreProperties(value = { "units", "photos" }, allowSetters = true)
    private BuildingProject project;

    @ManyToOne(fetch = LAZY)
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Unit unit;
}
