package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.dtos.CronogramaResponseDTO;
import com.example.tpfinanzas.dtos.GenerarCronogramaRequest;
import com.example.tpfinanzas.services.CronogramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cronograma")
@CrossOrigin
public class CronogramaController {

    @Autowired
    private CronogramaService service;

    @PostMapping
    public CronogramaResponseDTO generar(@RequestBody GenerarCronogramaRequest req) {
        return service.generar(req);
    }
}
