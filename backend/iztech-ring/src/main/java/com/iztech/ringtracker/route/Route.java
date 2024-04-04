package com.iztech.ringtracker.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iztech.ringtracker.stoponroute.StopOnRoute;
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
    private Set<StopOnRoute> stopOnRoutes = new HashSet<>();

    private boolean bidirectional;

    private int totalDurationMinutes;

    public void addRouteBusStop(StopOnRoute stopOnRoute) {
        stopOnRoutes.add(stopOnRoute);
        stopOnRoute.setRoute(this);
    }


}
