package com.iztech.ringtracker.stoponroute;

import com.iztech.ringtracker.route.Route;
import com.iztech.ringtracker.stop.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopOnRouteRepository extends JpaRepository<StopOnRoute, Long> {

    List<StopOnRoute> findByRoute(Route route);

    Optional<StopOnRoute> findByRouteIdAndStopId(Long routeId,Long stopId);
    Optional<StopOnRoute> findFirstByRouteOrderByArrivalTimeFromStartAsc(Route route);

    Optional<StopOnRoute> findFirstByRouteOrderByArrivalTimeFromStartDesc(Route route);

    List<StopOnRoute> findByRouteIdAndArrivalTimeFromStartGreaterThanOrderByArrivalTimeFromStartAsc(Long routeId, int currentStopArrivalTime);


}
