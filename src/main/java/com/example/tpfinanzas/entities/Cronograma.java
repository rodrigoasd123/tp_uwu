package com.example.tpfinanzas.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "cronogramas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cronograma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    private Integer periodo;
    private LocalDate fecha;

    private Double saldoInicial;
    private Double interes;
    private Double amortizacion;
    private Double cuota;
    private Double saldoFinal;

    @Column(length = 60)
    private String nota;
}
