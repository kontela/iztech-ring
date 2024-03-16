package com.iztech.ringtracker.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes/")
class RouteController {


    private final RouteService routeService;
    @Autowired
    RouteController(RouteService routeService){
        this.routeService=routeService;

    }

    @PostMapping
    Route createRoute(@RequestBody Route route) {
        return routeService.createRoute(route);
    }

}
