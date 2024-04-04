package com.iztech.ringtracker.routemanagement;

import com.iztech.ringtracker.bus.BusLocationUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RouteManagementService {

    private final LocationUpdateProcessor locationUpdateProcessor;
    private final RouteAssignmentCreator routeAssignmentCreator;
    private final RouteAssignmentTerminator routeAssignmentTerminator;

    @Autowired
    RouteManagementService(LocationUpdateProcessor locationUpdateProcessor,
                           RouteAssignmentCreator routeAssignmentCreator,
                           RouteAssignmentTerminator routeAssignmentTerminator) {
        this.locationUpdateProcessor = locationUpdateProcessor;
        this.routeAssignmentCreator = routeAssignmentCreator;
        this.routeAssignmentTerminator = routeAssignmentTerminator;
    }

    @Scheduled(fixedRate = 60000)
    void triggerRouteMatchingAndDematching() {
        System.out.println("Trigger -------");

        Instant now = Instant.now();
        routeAssignmentCreator.checkTimetableAndMatchRoutes(now);
        routeAssignmentCreator.matchUnmatchedRoutes(); // regular check
        routeAssignmentTerminator.handleRouteAssignmentTimeOut(now);
        routeAssignmentTerminator.handleBusTimeOut();

    }

    @EventListener
    void onBusLocationUpdate(BusLocationUpdateEvent event) {
        locationUpdateProcessor.processLocationUpdate(event.getBus(), event.getNewLocation());
        routeAssignmentTerminator.handleBusRouteCompletion(event.getBus(), event.getNewLocation());
    }


}
