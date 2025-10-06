package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.repositories.CronogramaRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/reportes") @CrossOrigin
public class ReportesController {
    private final CronogramaRepository croRepo;
    public ReportesController(CronogramaRepository r){ this.croRepo=r; }

    @GetMapping("/cronograma/{solicitudId}.csv")
    public ResponseEntity<String> csv(@PathVariable Long solicitudId){
        var filas = croRepo.findBySolicitudIdOrderByPeriodoAsc(solicitudId);
        StringBuilder sb = new StringBuilder("Periodo,Fecha,SaldoInicial,Interes,Amortizacion,Cuota,SaldoFinal,Nota\n");
        filas.forEach(f -> sb.append(f.getPeriodo()).append(",")
                .append(f.getFecha()).append(",")
                .append(f.getSaldoInicial()).append(",")
                .append(f.getInteres()).append(",")
                .append(f.getAmortizacion()).append(",")
                .append(f.getCuota()).append(",")
                .append(f.getSaldoFinal()).append(",")
                .append(f.getNota()==null? "":f.getNota().replace(",", " ")).append("\n"));
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.TEXT_PLAIN);
        h.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cronograma_"+solicitudId+".csv");
        return new ResponseEntity<>(sb.toString(), h, HttpStatus.OK);
    }
}
