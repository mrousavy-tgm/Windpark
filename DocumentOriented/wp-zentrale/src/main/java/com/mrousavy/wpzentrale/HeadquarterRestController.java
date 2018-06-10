package com.mrousavy.wpzentrale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeadquarterRestController {


    @GetMapping("/windparks")
    @ResponseBody
    public String getWindparks(){
        return "{ windpark: { id: 1 } }";
    }
}
