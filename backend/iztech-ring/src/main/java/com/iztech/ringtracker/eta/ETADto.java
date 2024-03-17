package com.iztech.ringtracker.eta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ETADto {
    private Long routeId;
    private String routeName;
    private Map<Long, Integer> stopIdToETAMap; // Maps stop ID to ETA in minutes

}