package com.example.matchuptracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
@Slf4j
@CrossOrigin("*")
public class SecureController {

    @GetMapping
    public String secureEndpoint() {
        return "Secure endpoint";
    }
}
