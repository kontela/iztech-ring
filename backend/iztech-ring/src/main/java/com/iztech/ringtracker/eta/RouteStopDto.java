package com.iztech.ringtracker.eta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class RouteStopDto {
        private long routeId;
        private long stopId;
        private int durationFromStart;

}
