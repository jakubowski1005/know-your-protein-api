package com.jakubowskiartur.knowyourprotein.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/map")
    public HashMap<String, HashMap<Integer, Double>> getMap() {
        Map map = new HashMap<Integer, Double>();
        map.put(1, .3d);

        Map map2 = new HashMap<String, HashMap<Integer, Double>>();
        map2.put("Hello", map);
        return (HashMap<String, HashMap<Integer, Double>>) map2;
    }
}
