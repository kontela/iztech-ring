package com.iztech.ringtracker.stop;

import com.iztech.ringtracker.route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stops/")
class StopController {

    private final StopService stopService;

    @Autowired
    StopController(StopService stopService){
        this.stopService=stopService;
    }

    @PostMapping
    Stop createStop(@RequestBody Stop stop) {
        return stopService.createStop(stop);
    }

}
