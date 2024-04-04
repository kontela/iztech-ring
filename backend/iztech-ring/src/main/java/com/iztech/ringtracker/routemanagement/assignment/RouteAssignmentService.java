package com.iztech.ringtracker.routemanagement.assignment;

import com.iztech.ringtracker.route.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteAssignmentService {

    private final RouteAssignmentRepository routeAssignmentRepository;


    public RouteAssignmentService(RouteAssignmentRepository routeAssignmentRepository) {
        this.routeAssignmentRepository = routeAssignmentRepository;
    }


    public List<RouteAssignment> getActiveAssignedRoutes() {
        return routeAssignmentRepository.findByMatchedTrue();
    }

    public RouteAssignment updateOrSaveRouteAssignment(RouteAssignment routeAssignment) {
        return routeAssignmentRepository.save(routeAssignment);

    }

    public List<RouteAssignment> getAllMatchedFalseRouteAssignments(){
        return routeAssignmentRepository.findByMatchedFalse();
    }
}
