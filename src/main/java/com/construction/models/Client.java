package com.construction.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = READ_WRITE)
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
    @Cache(usage = READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "unit" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    public Client(User user) {
        this.user = user;
    }
}
