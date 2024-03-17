package com.iztech.ringtracker.route;

import com.iztech.ringtracker._common_.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository){
        this.routeRepository=routeRepository;
    }
    Route createRoute(Route route) {
        return routeRepository.save(route);
    }



    public Route findById(Long id){

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found for id: " + id));

        return route;
    }

}
