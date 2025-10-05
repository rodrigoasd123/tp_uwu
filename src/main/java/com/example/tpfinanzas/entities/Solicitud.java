package com.example.tpfinanzas.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Solicitud {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true) @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = true) @JoinColumn(name = "unidad_id")
    private Unidad unidad;

    private LocalDate fecha;        // fecha de solicitud
    @Column(length = 5)  private String moneda;       // "PEN"/"USD"
    private Double monto;                              // principal
    @Column(length = 5)  private String tipoTasa;     // "TEA"/"TNA"
    private Double valorTasa;                          // anual
    private Integer capitalizacion;                    // m (si TNA)
    private Integer plazoMeses;                        // n
    private Integer graciaTotal;                       // meses
    private Integer graciaParcial;                     // meses
}
