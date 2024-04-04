package com.iztech.ringtracker.route;

import com.iztech.ringtracker._common.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository){
        this.routeRepository=routeRepository;
    }
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }



    public Route findById(Long id){

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found for id: " + id));

        return route;
    }
    public List<Route> findAll(){
        return routeRepository.findAll();
    }

}
