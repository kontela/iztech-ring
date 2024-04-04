package com.iztech.ringtracker.eta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eta/")
class ETAController {

    private final ETAService etaService;
    @Autowired
    ETAController(ETAService etaService){
        this.etaService=etaService;

    }



    @GetMapping
    ResponseEntity<List<RouteEtaDto>>getETAsForBusAssignedActiveRoutes(){

        List<RouteEtaDto> etas = etaService.calculateETAsForBusAssignedActiveRoutes();
        return ResponseEntity.ok(etas);
    }
}




