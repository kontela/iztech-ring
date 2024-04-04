package com.iztech.ringtracker.routemanagement;

import com.iztech.ringtracker._common.util.GeoUtil;
import com.iztech.ringtracker._common.exception.EntityNotFoundException;
import com.iztech.ringtracker.bus.Bus;
import com.iztech.ringtracker.bus.BusService;
import com.iztech.ringtracker.bus.Direction;
import com.iztech.ringtracker.bus.Location;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignment;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignmentRepository;
import com.iztech.ringtracker.stoponroute.StopOnRouteService;
import com.iztech.ringtracker.stop.Stop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class RouteAssignmentTerminator {


    private final StopOnRouteService stopOnRouteService;
    private final BusService busService;
    private final double proximityThresholdDistance;
    private final RouteAssignmentRepository routeAssignmentRepository;


    public RouteAssignmentTerminator(StopOnRouteService stopOnRouteService,
                                     RouteAssignmentRepository routeAssignmentRepository,
                                     BusService busService,
                                     @Value("${tracking.proximityThresholdDistance}") double proximityThresholdDistance) {
        this.stopOnRouteService = stopOnRouteService;
        this.routeAssignmentRepository = routeAssignmentRepository;
        this.busService = busService;
        this.proximityThresholdDistance = proximityThresholdDistance;
    }

    public void handleBusRouteCompletion(Bus bus, Location location) {

        Route currentRoute = bus.getCurrentRoute();


        if (currentRoute == null || (currentRoute.isBidirectional() && !bus.getDirection().equals(Direction.INBOUND))) {
            return;
        }

        Stop endStop = determineEndStop(bus, currentRoute);


        double dist = GeoUtil.calculateDistance(endStop.getLatitude(), endStop.getLongitude(), location.getLatitude(), location.getLongitude());

        System.out.println(dist);
        if (proximityThresholdDistance > dist) {
            completeRouteAssignment(bus);
        }
    }


    private Stop determineEndStop(Bus bus, Route currentRoute) {
        // This assumes that for bidirectional routes, inbound means heading back to the start stop

        return currentRoute.isBidirectional() ?
                stopOnRouteService.getStartStopForRoute(currentRoute).getStop() :
                stopOnRouteService.getEndStopForRoute(currentRoute).getStop();
    }

    private void completeRouteAssignment(Bus bus) {
        RouteAssignment routeAssignment = routeAssignmentRepository.findByBus(bus)
                .orElseThrow(() -> new EntityNotFoundException("RouteAssignment could not be found for given bus: " + bus));
        busService.resetBusByRoute(bus);
        routeAssignmentRepository.delete(routeAssignment);
    }

    public void handleRouteAssignmentTimeOut(Instant now) {

        List<RouteAssignment> routeAssignments = routeAssignmentRepository.findByEndOfLifeTimeLessThanEqual(now);

        for (RouteAssignment ra : routeAssignments) {
            if (ra.isMatched()) {
                busService.resetBusByRoute(ra.getBus());
            }
            routeAssignmentRepository.delete(ra);

        }
    }

    public void handleBusTimeOut() {
        List<Bus> inactiveBuses = busService.getInactiveBus();

        for (Bus bus : inactiveBuses) {
            detachAndResetBusFromRouteAssignment(bus);
        }
    }

    private void detachAndResetBusFromRouteAssignment(Bus bus) {
        RouteAssignment routeAssignment = routeAssignmentRepository.findByBus(bus)
                .orElseThrow(() -> new EntityNotFoundException("RouteAssignment could not be found for given bus: " + bus));
        routeAssignment.setBus(null);
        routeAssignment.setMatched(false);
        routeAssignmentRepository.save(routeAssignment);
        busService.resetBusByRoute(bus);

    }


}
