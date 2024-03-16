package com.iztech.ringtracker.stop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class StopService {

    private final StopRepository stopRepository;

    @Autowired
    StopService(StopRepository stopRepository){
        this.stopRepository= stopRepository;
    }

    Stop createStop(Stop stop) {
        return stopRepository.save(stop);
    }
}
