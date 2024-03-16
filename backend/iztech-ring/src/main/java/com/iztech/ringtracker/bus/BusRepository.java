package com.iztech.ringtracker.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BusRepository extends JpaRepository<Bus, Long> {
}
