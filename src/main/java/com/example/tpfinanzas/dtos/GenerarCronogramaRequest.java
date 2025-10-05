package com.example.tpfinanzas.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GenerarCronogramaRequest {
    private String moneda;          // "PEN" o "USD"
    private Double monto;           // Principal (P)
    private String tipoTasa;        // "TEA" o "TNA"
    private Double valorTasa;       // anual (ej. 0.12)
    private Integer capitalizacion; // si TNA, m por año (ej. 12)
    private Integer plazoMeses;     // n
    private Integer graciaTotal;    // meses
    private Integer graciaParcial;  // meses
    private LocalDate fechaDesembolso; // opcional (default hoy)
}
