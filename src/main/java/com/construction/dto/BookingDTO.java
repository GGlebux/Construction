package com.construction.dto;

import com.construction.models.enumeration.BookingStatus;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.construction.models.Booking} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO implements Serializable {

    private Long id;


    @NotNull
    private Instant bookingDate;

    @NotNull
    private BookingStatus status;

    private Instant expirationDate;

    private String note;

    @NotNull
    private BigDecimal price;

    @NotNull
    private ClientDTO client;

    @NotNull
    private UnitDTO unit;
}
