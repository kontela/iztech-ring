package com.iztech.ringtracker.stop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StopRepository extends JpaRepository<Stop,Long> {


}
