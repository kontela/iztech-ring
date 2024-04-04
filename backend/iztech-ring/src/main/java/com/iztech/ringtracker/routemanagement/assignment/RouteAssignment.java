package com.iztech.ringtracker.routemanagement.assignment;

import com.iztech.ringtracker.bus.Bus;
import com.iztech.ringtracker.route.Route;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
public class RouteAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Route route;

    @OneToOne
    private Bus bus;

    private boolean matched;

    private Instant endOfLifeTime;


}
