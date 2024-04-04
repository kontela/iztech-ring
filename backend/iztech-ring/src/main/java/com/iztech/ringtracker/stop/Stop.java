package com.iztech.ringtracker.stop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iztech.ringtracker.stoponroute.StopOnRoute;
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

    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StopOnRoute> routes = new HashSet<>();

    public void addRouteBusStop(StopOnRoute stopOnRoute) {
        routes.add(stopOnRoute);

    }
}