package com.iztech.ringtracker.bus;

import com.iztech.ringtracker._common.util.GeoUtil;
import com.iztech.ringtracker._common.exception.EntityNotFoundException;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.routemanagement.LocationUpdateProcessor;
import com.iztech.ringtracker.stop.Stop;
import com.iztech.ringtracker.stoponroute.StopOnRouteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public
class BusService {

    private final int maxGpsAgeMinutes;
    private final LocationRepository locationRepository;
    private final BusRepository busRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final StopOnRouteService stopOnRouteService;

    @Autowired
    public BusService(LocationRepository locationRepository,
                      BusRepository busRepository,
                      ApplicationEventPublisher eventPublisher,
                      StopOnRouteService stopOnRouteService,
                      @Value("${bus.location.defaultMinutes}") int maxGpsAgeMinutes) {
        this.locationRepository = locationRepository;
        this.busRepository = busRepository;
        this.eventPublisher = eventPublisher;
        this.maxGpsAgeMinutes = maxGpsAgeMinutes;
        this.stopOnRouteService = stopOnRouteService;
    }


    Bus createBus(Long id) {

        Bus bus = new Bus();
        bus.setId(id);
        return busRepository.save(bus);

    }

    public void updateBus(Bus bus) {
        busRepository.save(bus);
    }

    void updateBusLocation(Long busId, LocationDto locationDto) {


        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found for id: " + busId));


        Location location = new Location();
        Instant now = Instant.now();

        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setTimestamp(now);
        location.setBus(bus);

        locationRepository.save(location);

        bus.setLastGpsUpdateInstant(location.getTimestamp());
        busRepository.save(bus); //event

        BusLocationUpdateEvent event = new BusLocationUpdateEvent(bus, location);
        eventPublisher.publishEvent(event);
    }


    List<Location> getLatestBusLocations(int minutes) {
        Duration threshold = Duration.ofMinutes(minutes);
        Instant thresholdInstant = Instant.now().minus(threshold);

        List<Location> latestLocations = locationRepository.findLatestLocationForEveryBus(thresholdInstant);

        return latestLocations;

    }

    @Transactional
    public Optional<Bus> getClosestActiveBusFromPoint(Double latitude, Double longitude) {

        Instant gpsUpdateThreshold = Instant.now().minus(Duration.ofMinutes(maxGpsAgeMinutes));
        List<Bus> recentBuses = busRepository.findByLastGpsUpdateInstantAfter(gpsUpdateThreshold);

        return recentBuses.stream()
                .filter(bus -> {
                    Location busLocation = bus.getLocations().isEmpty() ? null : bus.getLocations().iterator().next();
                    return busLocation != null;
                })
                .min(Comparator.comparingDouble(bus -> {
                    Location busLocation = bus.getLocations().iterator().next();
                    return GeoUtil.calculateDistance(latitude, longitude, busLocation.getLatitude(), busLocation.getLongitude());
                }));

    }

    public void modifyBusByRoute(Bus bus, Route route) {
        bus.setCurrentRoute(route);

        if (route.isBidirectional()) {
            bus.setDirection(Direction.OUTBOUND);

        } else {
            bus.setDirection(Direction.ROUND_TRIP);
        }
        Location latestLocation = locationRepository.findTopByBusOrderByTimestampDesc(bus)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found for id: " + bus.getId()));

        Stop firstStop =stopOnRouteService.getStartStopForRoute(route).getStop();
        bus.setCurrentStop(firstStop);
        busRepository.save(bus);

    }

    public void resetBusByRoute(Bus bus) {
        bus.setCurrentRoute(null);
        bus.setCurrentStop(null);
        bus.setDirection(Direction.UNKNOWN);
        updateBus(bus);
    }

    public List<Bus> getInactiveBus() {
        Instant gpsUpdateThreshold = Instant.now().minus(Duration.ofMinutes(maxGpsAgeMinutes));
        List<Bus> inactiveBus = busRepository.findByLastGpsUpdateInstantBefore(gpsUpdateThreshold);
        return inactiveBus;
    }

}
