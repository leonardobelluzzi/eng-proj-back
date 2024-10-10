package br.com.fatec.fatec_eng3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {

    @GetMapping("/health")
    public String hello() {
        return "Health check";
    }
}