package com.construction.models;

import com.construction.models.enumeration.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

import static com.construction.models.enumeration.BookingStatus.ACTIVE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.time.Duration.ofDays;
import static java.time.Instant.now;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private static final BigDecimal bookingPercentage = new BigDecimal("0.05");

    public Booking(String note, Client client, Unit unit) {
        this.note = note;
        this.client = client;
        this.unit = unit;
        this.price = unit.getPrice()
                .multiply(bookingPercentage)
                .setScale(0, ROUND_HALF_UP);
    }
}
