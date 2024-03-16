package com.iztech.ringtracker.eta;

import com.iztech.ringtracker.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RouteStopRepository extends JpaRepository<RouteStop, Long> {

    List<RouteStop> findByRoute(Route route);
}
