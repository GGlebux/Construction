package com.construction.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = LAZY, mappedBy = "client")
    @JsonIgnoreProperties(value = { "client", "unit" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    public Client(User user) {
        this.user = user;
    }
}
