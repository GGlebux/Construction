package com.construction.domain;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import com.construction.domain.enumeration.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private Instant bookingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "bookings" }, allowSetters = true)
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Unit unit;
}
