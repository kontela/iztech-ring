package com.iztech.ringtracker.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableService {


    private final TimetableRepository timetableRepository;

    private final RouteService routeService;
    @Autowired
    TimetableService(TimetableRepository timetableRepository, RouteService routeService){
        this.timetableRepository = timetableRepository;
        this.routeService=routeService;
    }

    public Timetable addOrUpdateTimetable(Timetable t){
        return timetableRepository.save(t);
    }

    Timetable addDepartureTimeToRoute(Long routeId, LocalTime departureTime, Boolean weekendActivity) {
        Timetable s =new Timetable();
        s.setDepartureTime(departureTime);
        s.setRoute(routeService.findById(routeId));
        s.setWeekendActivity(weekendActivity);
        Timetable save = timetableRepository.save(s);
        return save;
    }

    public List<Route> findRoutesByDepartureTime(Instant now) {

        ZoneId zoneId = ZoneId.of("Europe/Istanbul");
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        LocalTime localTime = zonedDateTime.toLocalTime();

        LocalTime t = localTime.truncatedTo(ChronoUnit.MINUTES);
        boolean weekendActive = zonedDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || zonedDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;

        List<Timetable> departures = timetableRepository.findByDepartureTimeAndWeekendActivity(t,weekendActive);

        return departures.stream()
                .map(Timetable::getRoute)
                .distinct()
                .collect(Collectors.toList());

    }

}
