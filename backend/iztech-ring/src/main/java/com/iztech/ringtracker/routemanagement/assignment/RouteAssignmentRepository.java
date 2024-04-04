package com.iztech.ringtracker.routemanagement.assignment;

import com.iztech.ringtracker.bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteAssignmentRepository extends JpaRepository<RouteAssignment,Long> {

    List<RouteAssignment> findByMatchedFalse();

    List<RouteAssignment> findByMatchedTrue();
    Optional<RouteAssignment> findByBus(Bus bus);

    List<RouteAssignment> findByEndOfLifeTimeLessThanEqual(Instant selectedTime);
}
