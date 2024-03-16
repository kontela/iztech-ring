package com.iztech.ringtracker.eta;

import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.route.RouteRepository;
import com.iztech.ringtracker.stop.Stop;
import com.iztech.ringtracker.stop.StopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ETAService {


    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final RouteStopRepository routeStopRepository;

    @Autowired
    ETAService(RouteRepository routeRepository, StopRepository stopRepository,RouteStopRepository routeStopRepository){
        this.routeRepository=routeRepository;
        this.stopRepository=stopRepository;
        this.routeStopRepository=routeStopRepository;
    }



    RouteStop createRouteStop(long routeId, long stopId, int durationFromStart){

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found for id: " + routeId));

        Stop stop= stopRepository.findById(stopId)
                .orElseThrow(() -> new EntityNotFoundException("Stop not found for id: " + stopId));

        RouteStop routeStop = new RouteStop(route, stop, durationFromStart);
        return routeStopRepository.save(routeStop);

           }

     ETADto calculateETAForRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found for id: " + routeId));

        List<RouteStop> routestops = routeStopRepository.findByRoute(route);

        //continue business logic here with static eta calculation logic.
         // route time table fetch and find forecoming time of bus
         // accordingly use difference function to get it done.

         //perhaps change the name of this class to ETAStatic


        return null;
    }
}

