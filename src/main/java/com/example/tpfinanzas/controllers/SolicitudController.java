package com.example.tpfinanzas.controllers;

import com.example.tpfinanzas.dtos.*;
import com.example.tpfinanzas.entities.*;
import com.example.tpfinanzas.repositories.*;
import com.example.tpfinanzas.services.CronogramaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/solicitudes") @CrossOrigin
public class SolicitudController {
    private final SolicitudRepository solRepo;
    private final ClienteRepository cliRepo;
    private final UnidadRepository uniRepo;
    private final CronogramaRepository croRepo;
    private final CronogramaService cronService;

    public SolicitudController(SolicitudRepository s, ClienteRepository c, UnidadRepository u,
                               CronogramaRepository cr, CronogramaService cronService){
        this.solRepo=s; this.cliRepo=c; this.uniRepo=u; this.croRepo=cr; this.cronService=cronService;
    }

    @GetMapping public List<Solicitud> listar(){ return solRepo.findAll(); }

    @PostMapping public Solicitud crear(@RequestBody SolicitudDTO dto){
        Solicitud s = Solicitud.builder()
                .cliente(dto.getIdCliente()==null? null: cliRepo.findById(dto.getIdCliente()).orElse(null))
                .unidad(dto.getIdUnidad()==null? null: uniRepo.findById(dto.getIdUnidad()).orElse(null))
                .fecha(dto.getFecha())
                .moneda(dto.getMoneda()).monto(dto.getMonto())
                .tipoTasa(dto.getTipoTasa()).valorTasa(dto.getValorTasa())
                .capitalizacion(dto.getCapitalizacion()).plazoMeses(dto.getPlazoMeses())
                .graciaTotal(dto.getGraciaTotal()).graciaParcial(dto.getGraciaParcial())
                .build();
        return solRepo.save(s);
    }

    /** Genera y guarda el cronograma de una solicitud */
    @PostMapping("/{id}/cronograma")
    public CronogramaResponseDTO generarYGuardar(@PathVariable Long id){
        Solicitud s = solRepo.findById(id).orElseThrow();
        var req = GenerarCronogramaRequest.builder()
                .moneda(s.getMoneda()).monto(s.getMonto())
                .tipoTasa(s.getTipoTasa()).valorTasa(s.getValorTasa())
                .capitalizacion(s.getCapitalizacion()).plazoMeses(s.getPlazoMeses())
                .graciaTotal(s.getGraciaTotal()).graciaParcial(s.getGraciaParcial())
                .fechaDesembolso(s.getFecha()).build();
        var resp = cronService.generar(req);

        // Limpiar y guardar
        croRepo.findBySolicitudIdOrderByPeriodoAsc(id).forEach(croRepo::delete);
        int i=0;
        for (var f : resp.getCronograma()){
            Cronograma c = Cronograma.builder()
                    .solicitud(s).periodo(++i).fecha(f.getFecha())
                    .saldoInicial(f.getSaldoInicial()).interes(f.getInteres())
                    .amortizacion(f.getAmortizacion()).cuota(f.getCuota())
                    .saldoFinal(f.getSaldoFinal()).nota(f.getNota())
                    .build();
            croRepo.save(c);
        }
        return resp;
    }

    @GetMapping("/{id}/cronograma")
    public List<Cronograma> verCronograma(@PathVariable Long id){
        return croRepo.findBySolicitudIdOrderByPeriodoAsc(id);
    }
}
