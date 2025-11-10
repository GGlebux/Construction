package com.construction.models;

import com.construction.models.enumeration.BookingStatus;
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

import static com.construction.models.enumeration.BookingStatus.ACTIVE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.Duration.ofDays;
import static java.time.Instant.now;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private Instant bookingDate = now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = ACTIVE;

    @Column(name = "expiration_date")
    private Instant expirationDate = now().plus(ofDays(14));

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @JoinColumn(columnDefinition = "client_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "user", "bookings" }, allowSetters = true)
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(columnDefinition = "unit_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "photos", "bookings", "project" }, allowSetters = true)
    private Unit unit;

    public Booking(String note, Client client, Unit unit) {
        this.note = note;
        this.client = client;
        this.unit = unit;
    }
}
