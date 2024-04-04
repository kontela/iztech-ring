package com.iztech.ringtracker.stoponroute;

import com.iztech.ringtracker._common.util.GeoUtil;
import com.iztech.ringtracker._common.exception.EntityNotFoundException;
import com.iztech.ringtracker.bus.Location;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.route.RouteService;
import com.iztech.ringtracker.stop.Stop;
import com.iztech.ringtracker.stop.StopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopOnRouteService {

    private final StopOnRouteRepository stopOnRouteRepository;
    private final RouteService routeService;
    private final StopService stopService;

    public StopOnRouteService(StopOnRouteRepository stopOnRouteRepository, RouteService routeService, StopService stopService) {
        this.stopOnRouteRepository = stopOnRouteRepository;
        this.routeService=routeService;
        this.stopService=stopService;
    }

    public StopOnRoute getStartStopForRoute(Route route) {
        return stopOnRouteRepository.findFirstByRouteOrderByArrivalTimeFromStartAsc(route)
                .orElseThrow(() -> new EntityNotFoundException("No stops found for this route"));
    }

    public StopOnRoute getEndStopForRoute(Route route) {
        return stopOnRouteRepository.findFirstByRouteOrderByArrivalTimeFromStartDesc(route)
                .orElseThrow(() -> new EntityNotFoundException("No stops found for this route"));
    }

    public StopOnRoute getClosestStopFromPoint(Route route, Location location) {
        List<StopOnRoute> stops = stopOnRouteRepository.findByRoute(route);

        StopOnRoute closestStopOnRoute = stops.getFirst();
        double min = Double.MAX_VALUE;

        for (StopOnRoute stopOnRoute : stops) {
            Stop stop = stopOnRoute.getStop();
            double dist = GeoUtil.calculateDistance(stop.getLatitude(), stop.getLongitude(), location.getLatitude(), location.getLongitude());

            if (min > dist) {
                closestStopOnRoute = stopOnRoute;
                min = dist;
            }
        }
        return closestStopOnRoute;
    }

    public StopOnRoute createRouteStop(long routeId, long stopId, int durationFromStart) {
        Route route = routeService.findById(routeId);

        Stop stop = stopService.findById(stopId);

        StopOnRoute stopOnRoute = new StopOnRoute(route, stop, durationFromStart);
        return stopOnRouteRepository.save(stopOnRoute);

    }

    public List<StopOnRoute> findFutureStops(Route route, Stop stop){
        StopOnRoute stopOnRoute =stopOnRouteRepository.findByRouteIdAndStopId(route.getId(),stop.getId())
                .orElseThrow(()-> new EntityNotFoundException("StopOnRoute not found for stop id " + stop));

        return stopOnRouteRepository.findByRouteIdAndArrivalTimeFromStartGreaterThanOrderByArrivalTimeFromStartAsc(route.getId(),stopOnRoute.getArrivalTimeFromStart());

    }
}
