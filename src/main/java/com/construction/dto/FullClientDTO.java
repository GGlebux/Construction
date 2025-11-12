package com.construction.dto;

import com.construction.models.Booking;
import com.construction.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

public class FullClientDTO {
    private Long id;
    private Set<BookingDTO> bookings = new HashSet<>();
}
