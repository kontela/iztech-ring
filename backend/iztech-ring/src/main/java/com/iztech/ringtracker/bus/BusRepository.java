package com.iztech.ringtracker.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findByLastGpsUpdateInstantAfter(Instant threshold);

    List<Bus> findByLastGpsUpdateInstantBefore(Instant threshold);

}
