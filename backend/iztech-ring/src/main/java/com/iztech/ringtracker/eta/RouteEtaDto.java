package com.iztech.ringtracker.eta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteEtaDto {

    private Long busId;
    private Long routeId;
    private List<StopEtaDto> etas;

    // Constructors, getters, and setters

}
