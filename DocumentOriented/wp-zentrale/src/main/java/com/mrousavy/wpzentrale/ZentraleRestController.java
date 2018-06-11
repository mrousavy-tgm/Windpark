package com.mrousavy.wpzentrale;

import com.mrousavy.wpzentrale.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZentraleRestController {
    private final WindparkRepository windparks;

    private final WindparkVersionRepository windparkVersions;

    private final WindradRepository windrads;

    @Autowired
    public ZentraleRestController(WindparkRepository windparks, WindparkVersionRepository windparkVersions, WindradRepository windrads) {
        this.windparks = windparks;
        this.windparkVersions = windparkVersions;
        this.windrads = windrads;
    }

    @GetMapping("/uniquewindparks")
    public List<Windpark> uniqueWindparks(){
        List<WindparkVersion> allWindparks = windparkVersions.findAll();
        List<Windpark> windparks = new ArrayList<>();
        for(WindparkVersion version: allWindparks){
            if(!windparks.contains(version.getWindpark())){
                windparks.add(version.getWindpark());
            }
        }
        return windparks;
    }

    @GetMapping("/windparks")
    public List<Windpark> windparks(){
        return windparks.findAll();
    }

    @GetMapping("/windpark")
    public Windpark windpark(@RequestParam(value="id") String id){
        return windparks.findByid(id);
    }

    @GetMapping("/fetch")
    public void fetch() {
        DataAccessLayer dal = new DataAccessLayer(windparks, windparkVersions, windrads);
        dal.fetch();
    }
}
