package com.iztech.ringtracker.eta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StopEtaDto {
    private Long stopId;
    private String stopName;
    private int arrivalTime;
}
