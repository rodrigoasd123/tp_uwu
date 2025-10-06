package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.repositories.CronogramaRepository;
import com.example.tpfinanzas.services.IndicadoresService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/indicadores") @CrossOrigin
public class IndicadoresController {
    private final CronogramaRepository croRepo;
    private final IndicadoresService svc;

    public IndicadoresController(CronogramaRepository r, IndicadoresService s){ this.croRepo=r; this.svc=s; }

    @GetMapping("/solicitud/{id}")
    public Map<String, Double> indicadores(@PathVariable Long id,
                                           @RequestParam(defaultValue="0") double gastosFijosMensuales){
        var filas = croRepo.findBySolicitudIdOrderByPeriodoAsc(id);
        return svc.indicadoresDesdeCronograma(filas, gastosFijosMensuales);
    }
}
