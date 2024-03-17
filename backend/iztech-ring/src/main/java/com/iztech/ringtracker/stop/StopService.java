package com.iztech.ringtracker.stop;

import com.iztech.ringtracker._common_.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopService {

    private final StopRepository stopRepository;

    @Autowired
    public StopService(StopRepository stopRepository){
        this.stopRepository= stopRepository;
    }

    public Stop createStop(Stop stop) {
        return stopRepository.save(stop);
    }

    public Stop findById(Long id){

        Stop stop= stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stop not found for id: " + id));

        return stop;
    }


}
