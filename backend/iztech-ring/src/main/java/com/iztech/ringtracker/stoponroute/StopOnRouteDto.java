package com.iztech.ringtracker.stoponroute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopOnRouteDto {
        private long routeId;
        private long stopId;
        private int durationFromStart;

}
