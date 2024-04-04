package com.iztech.ringtracker.routemanagement;

import com.iztech.ringtracker.bus.Bus;
import com.iztech.ringtracker.bus.BusService;
import com.iztech.ringtracker.bus.Direction;
import com.iztech.ringtracker.bus.Location;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.stoponroute.StopOnRoute;
import com.iztech.ringtracker.stoponroute.StopOnRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocationUpdateProcessor {

    private final BusService busService;
    private final StopOnRouteService stopOnRouteService;
    private final double proximityThresholdDistance;

    @Autowired
    LocationUpdateProcessor(BusService busService, StopOnRouteService stopOnRouteService, @Value("${tracking.proximityThresholdDistance}") double proximityThresholdDistance) {

        this.busService = busService;
        this.stopOnRouteService = stopOnRouteService;
        this.proximityThresholdDistance = proximityThresholdDistance;
    }

    public void processLocationUpdate(Bus bus, Location newLocation) {

        // If more conditioned actions are added, then strategy pattern can be implemented

        if (null == bus.getCurrentRoute()) {
            return; //exit if bus is not paired with route.
        }
        updateCurrentStop(bus, newLocation);
        updateDirection(bus, newLocation);

        // must setted route has
        // get and set nearby bus stop
        // check the direction - if it reaches end point then switch it.

    }

    private void updateCurrentStop(Bus bus, Location location) {

        Route currentRoute = bus.getCurrentRoute();

        StopOnRoute stopOnRoute = stopOnRouteService.getClosestStopFromPoint(currentRoute, location);

        bus.setCurrentStop(stopOnRoute.getStop());
        busService.updateBus(bus);


    }

    private void updateDirection(Bus bus, Location newLocation) {

        Route currentRoute = bus.getCurrentRoute();
        if (currentRoute.isBidirectional()) {
            StopOnRoute closestStop = stopOnRouteService.getClosestStopFromPoint(currentRoute, newLocation);
            StopOnRoute endStop = stopOnRouteService.getEndStopForRoute(currentRoute);

            System.out.println("Update direction trial");
            System.out.println(closestStop.getStop().getName());
            System.out.println(endStop.getStop().getName());
            System.out.println("So it should work");

            if ((closestStop.equals(endStop)) && (bus.getDirection().equals(Direction.OUTBOUND))) {
                bus.setDirection(Direction.INBOUND);
                busService.updateBus(bus);
            }
        }


    }
}
