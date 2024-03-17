package com.iztech.ringtracker.eta;

import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.route.RouteService;
import com.iztech.ringtracker.stop.Stop;

import com.iztech.ringtracker.stop.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ETAService {


    private final RouteService routeService;
    private final StopService stopService;
    private final RouteStopRepository routeStopRepository;

    @Autowired
    ETAService(RouteService routeService, StopService stopService,RouteStopRepository routeStopRepository){
        this.routeService=routeService;
        this.stopService=stopService;
        this.routeStopRepository=routeStopRepository;
    }



    RouteStop createRouteStop(long routeId, long stopId, int durationFromStart){

        Route route = routeService.findById(routeId);

        Stop stop = stopService.findById(stopId);

        RouteStop routeStop = new RouteStop(route, stop, durationFromStart);
        return routeStopRepository.save(routeStop);

           }

     ETADto calculateETAForRoute(Long routeId) {

        Route route = routeService.findById(routeId);

        List<RouteStop> routestops = routeStopRepository.findByRoute(route);

        //continue business logic here with static eta calculation logic.
         // route time table fetch and find forecoming time of bus
         // accordingly use difference function to get it done.

         //perhaps change the name of this class to ETAStatic


        return null;
    }
}

