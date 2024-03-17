package com.iztech.ringtracker.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RouteRepository extends JpaRepository<Route, Long> {

}
