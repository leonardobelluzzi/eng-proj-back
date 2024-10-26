package br.com.fatec.fatec_eng3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.fatec.fatec_eng3.service.StartService;

@RestController
public class Health {

    @Autowired
    private StartService startService;

    @GetMapping("/health")
    public String hello() {
        return "Health check";
    }


    @GetMapping("/initializar")
    public String init() throws JsonMappingException, JsonProcessingException {

        startService.initilizationQuest();
        return "Deu certo";
    }



}