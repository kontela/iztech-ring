package com.iztech.ringtracker.bus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    private Double latitude;
    private Double longitude;
    private Instant timestamp;
}
