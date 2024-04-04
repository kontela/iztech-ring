package com.iztech.ringtracker.stoponroute;

import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.stop.Stop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StopOnRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private Stop stop;

    private int arrivalTimeFromStart;
    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public StopOnRoute(Route route, Stop stop, int arrivalTimeFromStart) {
        this.route = route;
        this.stop = stop;
        this.arrivalTimeFromStart=arrivalTimeFromStart;
        route.addRouteBusStop(this);
        stop.addRouteBusStop(this);
    }
}




