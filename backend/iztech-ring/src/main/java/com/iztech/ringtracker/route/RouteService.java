package com.iztech.ringtracker.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    RouteService(RouteRepository routeRepository){
        this.routeRepository=routeRepository;
    }
    Route createRoute(Route route) {
        return routeRepository.save(route);
    }

}
