package com.iztech.ringtracker.stoponroute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StopOnRouteController {

    private final StopOnRouteService stopOnRouteService;
    @Autowired
    public StopOnRouteController(StopOnRouteService stopOnRouteService){
        this.stopOnRouteService = stopOnRouteService;
    }

    @PostMapping
    StopOnRoute bindRouteStop(@RequestBody StopOnRouteDto dto) {
        return stopOnRouteService.createRouteStop(dto.getRouteId(), dto.getStopId(), dto.getDurationFromStart());
    }

}
