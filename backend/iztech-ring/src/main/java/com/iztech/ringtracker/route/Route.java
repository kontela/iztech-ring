package com.iztech.ringtracker.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iztech.ringtracker.eta.RouteStop;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Route {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RouteStop> routeStops = new HashSet<>();

    public void addRouteBusStop(RouteStop routeStop) {
        routeStops.add(routeStop);
        routeStop.setRoute(this);
    }

}
