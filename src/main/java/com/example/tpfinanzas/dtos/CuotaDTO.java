package com.example.tpfinanzas.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CuotaDTO {
    private Integer periodo;
    private LocalDate fecha;
    private Double saldoInicial;
    private Double interes;
    private Double amortizacion;
    private Double cuota;
    private Double saldoFinal;
    private String nota;
}
