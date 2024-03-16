package com.iztech.ringtracker.bus;

import com.iztech.ringtracker._common_.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
class BusService {

    private final LocationRepository locationRepository;
    private final BusRepository busRepository;

    @Autowired
    public BusService(LocationRepository locationRepository, BusRepository busRepository){
        this.locationRepository = locationRepository;
        this.busRepository=busRepository;
    }


    Bus createBus(Long id){

        Bus bus = new Bus();
        bus.setId(id);
        return busRepository.save(bus);

    }

    void updateBusLocation(Long busId, LocationDto locationDto){

        //handle later this exception on exception advice aspect

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found for id: " + busId));

        Location location = new Location();
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setTimestamp(Instant.now());
        location.setBus(bus);

        locationRepository.save(location);
    }


     List<Location> getLatestBusLocations(int minutes) {
        Duration threshold = Duration.ofMinutes(minutes);
        Instant thresholdInstant = Instant.now().minus(threshold);

        List<Location> latestLocations = locationRepository.findLatestLocationForEveryBus(thresholdInstant);

        return latestLocations;

    }
}
