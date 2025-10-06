package com.example.tpfinanzas.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitudDTO {
    private Long idCliente;
    private Long idUnidad;
    private String moneda;          // PEN / USD
    private Double monto;
    private String tipoTasa;        // TEA / TNA
    private Double valorTasa;       // anual
    private Integer capitalizacion; // si TNA, m por año
    private Integer plazoMeses;     // n
    private Integer graciaTotal;    // meses
    private Integer graciaParcial;  // meses
    private LocalDate fecha;        // fecha de solicitud / desembolso
}
