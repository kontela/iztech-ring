package com.iztech.ringtracker.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable,Long> {

    List<Timetable> findByRoute(Route route);

    List<Timetable> findByDepartureTimeAndWeekendActivity(LocalTime time, boolean weekendActive);
}
