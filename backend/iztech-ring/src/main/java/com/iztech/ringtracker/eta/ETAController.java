package com.iztech.ringtracker.eta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eta/")
class ETAController {

    private final ETAService etaService;
    @Autowired
    ETAController(ETAService etaService){
        this.etaService=etaService;

    }

    @PostMapping
    RouteStop bindRouteStop(@RequestBody RouteStopDto dto) {
        return etaService.createRouteStop(dto.getRouteId(), dto.getStopId(), dto.getDurationFromStart());
    }


    @GetMapping("{routeId}")
    ResponseEntity<ETADto> getETAForRoute(@PathVariable Long routeId) {
        ETADto etaDto = etaService.calculateETAForRoute(routeId);
        return ResponseEntity.ok(etaDto);
    }
}




