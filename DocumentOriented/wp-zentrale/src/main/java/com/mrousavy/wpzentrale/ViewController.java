package com.mrousavy.wpzentrale;

import com.mrousavy.wpzentrale.model.Windpark;
import com.mrousavy.wpzentrale.model.WindparkRepository;
import com.mrousavy.wpzentrale.model.WindparkVersion;
import com.mrousavy.wpzentrale.model.WindparkVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    private final WindparkRepository windparks;
    private final WindparkVersionRepository windparkVersions;

    @Autowired
    public ViewController(WindparkRepository windparks, WindparkVersionRepository windparkVersions) {
        this.windparks = windparks;
        this.windparkVersions = windparkVersions;
    }

    @GetMapping("/graphic/windparks")
    public String windparks(Model model){
        List<WindparkVersion> allWindparks = windparkVersions.findAll();
        List<Windpark> windparks = new ArrayList<>();
        for(WindparkVersion version: allWindparks){
            if(!windparks.contains(version.getWindpark())){
                windparks.add(version.getWindpark());
            }
        }

        model.addAttribute("windparks", windparks);

        return "windparks";
    }

    @GetMapping("/graphic/windpark")
    public String windpark(Model model, @RequestParam(value="id") String id){
        model.addAttribute("windpark", windparks.findByid(id));
        return "windpark";
    }
}