package com.iztech.ringtracker.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l FROM Location l WHERE l.timestamp IN (SELECT MAX(l2.timestamp) FROM Location l2 GROUP BY l2.bus ) AND l.timestamp > :thresholdTime")
    List<Location> findLatestLocationForEveryBus(@Param("thresholdTime") Instant thresholdTime);

}
