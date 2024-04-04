package com.iztech.ringtracker.routemanagement;

import com.iztech.ringtracker.bus.Bus;
import com.iztech.ringtracker.bus.BusService;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.route.TimetableService;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignment;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignmentService;
import com.iztech.ringtracker.stoponroute.StopOnRoute;
import com.iztech.ringtracker.stoponroute.StopOnRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Component
public class RouteAssignmentCreator {

    private final BusService busService;
    private final TimetableService timetableService;
    private final StopOnRouteService stopOnRouteService;
    private final RouteAssignmentService routeAssignmentService;

    @Autowired
    RouteAssignmentCreator(TimetableService timetableService, RouteAssignmentService routeAssignmentService, StopOnRouteService stopOnRouteService, BusService busService) {
        this.timetableService = timetableService;
        this.routeAssignmentService = routeAssignmentService;
        this.stopOnRouteService = stopOnRouteService;
        this.busService = busService;
    }


    void checkTimetableAndMatchRoutes(Instant now) {

        System.out.println("Timetable check and set started");
        List<Route> routesToMatch = timetableService.findRoutesByDepartureTime(now);

        for (Route route : routesToMatch) {
            System.out.println(routesToMatch.getFirst().getId());
            RouteAssignment routeAssignment = new RouteAssignment();
            routeAssignment.setRoute(route);
            System.out.println("Ra created");

            routeAssignment.setEndOfLifeTime(now.plus(Duration.ofMinutes(route.getTotalDurationMinutes())));

            Optional<Bus> bus = matchToBus(route);
            System.out.println("if bus present then it is assigned");


            if (bus.isPresent()) {
                System.out.println(bus.get().getId());
                System.out.println("setliyor");

                setRouteAssignmentAndBus(routeAssignment, bus.get());


            } else {
                System.out.println("setlemedi");

                routeAssignment.setMatched(false);
                routeAssignmentService.updateOrSaveRouteAssignment(routeAssignment);
            }
            System.out.println("saveledi");

        }


    }

    void matchUnmatchedRoutes() {
        List<RouteAssignment> routesToMatch = routeAssignmentService.getAllMatchedFalseRouteAssignments();

        for (RouteAssignment routeAssignment : routesToMatch) {
            Optional<Bus> b = matchToBus(routeAssignment.getRoute());

            b.ifPresent(bus -> setRouteAssignmentAndBus(routeAssignment, bus));

        }

    }

    private Optional<Bus> matchToBus(Route route) {

        StopOnRoute startStopOnRoute = stopOnRouteService.getStartStopForRoute(route);
        Double latitude = startStopOnRoute.getStop().getLatitude();
        Double longitude = startStopOnRoute.getStop().getLongitude();

        return busService.getClosestActiveBusFromPoint(latitude, longitude); //maybe extra safeguard, if it is assigned dont assign it


    }


    private void setRouteAssignmentAndBus(RouteAssignment routeAssignment, Bus closestValidBus) {
        routeAssignment.setMatched(true);
        routeAssignment.setBus(closestValidBus);
        routeAssignmentService.updateOrSaveRouteAssignment(routeAssignment);
        busService.modifyBusByRoute(closestValidBus, routeAssignment.getRoute());
    }
}
