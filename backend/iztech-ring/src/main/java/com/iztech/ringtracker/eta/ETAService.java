package com.iztech.ringtracker.eta;

import com.iztech.ringtracker.bus.Bus;
import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.route.RouteService;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignment;
import com.iztech.ringtracker.routemanagement.assignment.RouteAssignmentService;
import com.iztech.ringtracker.stoponroute.StopOnRoute;

import com.iztech.ringtracker.stop.StopService;
import com.iztech.ringtracker.stoponroute.StopOnRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ETAService {


    private final RouteService routeService;
    private final StopService stopService;
    private final StopOnRouteService stopOnRouteService;
    private final RouteAssignmentService routeAssignmentService;

    @Autowired
    ETAService(RouteService routeService, StopService stopService, StopOnRouteService stopOnRouteService, RouteAssignmentService routeAssignmentService) {
        this.routeService = routeService;
        this.stopService = stopService;
        this.stopOnRouteService = stopOnRouteService;
        this.routeAssignmentService = routeAssignmentService;
    }



    List<RouteEtaDto> calculateETAsForBusAssignedActiveRoutes() {

        List<RouteAssignment> routeAssignmentList = routeAssignmentService.getActiveAssignedRoutes();

        List<RouteEtaDto> routeEtaDtoList = new ArrayList<RouteEtaDto>();

        for (RouteAssignment routeAssignment : routeAssignmentList) {
            RouteEtaDto routeEtaDto = prepareRouteEtaDto(routeAssignment);
            routeEtaDtoList.add(routeEtaDto);

        }

        return routeEtaDtoList;
    }

    private RouteEtaDto prepareRouteEtaDto(RouteAssignment routeAssignment) {
        RouteEtaDto routeEtaDto = new RouteEtaDto();

        Bus bus = routeAssignment.getBus();
        Route route = routeAssignment.getRoute();

        routeEtaDto.setBusId(bus.getId());
        routeEtaDto.setRouteId(route.getId());

        List<StopOnRoute> futureStops = stopOnRouteService.findFutureStops(route, bus.getCurrentStop());
        List<StopEtaDto> etaDtoList = prepareStopEtaDtos(futureStops);

        routeEtaDto.setEtas(etaDtoList);

        return routeEtaDto;
    }

    private List<StopEtaDto> prepareStopEtaDtos(List<StopOnRoute> futureStops) {
        List<StopEtaDto> etaDtoList = new ArrayList<>();
        int firstStopArrivalTime = futureStops.isEmpty() ? 0 : futureStops.get(0).getArrivalTimeFromStart();

        for (StopOnRoute stop : futureStops) {
            StopEtaDto etaDto = new StopEtaDto();
            etaDto.setStopId(stop.getStop().getId());
            etaDto.setStopName(stop.getStop().getName());

            int minutesUntilArrival = stop.getArrivalTimeFromStart() - firstStopArrivalTime;
            etaDto.setArrivalTime(minutesUntilArrival);

            etaDtoList.add(etaDto);
        }

        return etaDtoList;
    }




}

