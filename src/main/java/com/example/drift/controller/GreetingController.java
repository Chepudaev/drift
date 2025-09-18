package com.example.drift.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/api")
    public String greeting(){
        return "Hello World";
    }
}
