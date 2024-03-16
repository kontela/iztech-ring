package com.iztech.ringtracker.stop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iztech.ringtracker.eta.RouteStop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "stop")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RouteStop> routes = new HashSet<>();

    public void addRouteBusStop(RouteStop routeStop) {
        routes.add(routeStop);

    }
}