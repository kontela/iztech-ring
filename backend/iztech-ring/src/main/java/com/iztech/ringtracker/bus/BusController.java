package com.iztech.ringtracker.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
class BusController {

    private final BusService busService;

    @Autowired
    BusController(BusService busService){
        this.busService= busService;
    }


    @PostMapping
    Bus createBus(@RequestBody BusDto dto) {
        return busService.createBus(dto.getId());
    }


    @PostMapping
    @RequestMapping("/{busId}/locations")
    void updateBusLocation(@PathVariable Long busId,@RequestBody LocationDto locationDto){

        busService.updateBusLocation(busId, locationDto);

    }

    @GetMapping("/locations/latest")
    ResponseEntity<List<Location>> getLatestBusLocationsWithinThreshold(
            @RequestParam(value = "minutes", defaultValue = "30") int minutes) {
        List<Location> recentLocations = busService.getLatestBusLocations(minutes);
        return ResponseEntity.ok(recentLocations);
    }

}
