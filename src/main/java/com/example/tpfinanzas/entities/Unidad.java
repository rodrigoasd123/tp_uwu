package com.example.tpfinanzas.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unidades")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Unidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100) private String proyecto;
    @Column(length = 200) private String direccion;
    @Column(length = 60)  private String distrito;
    @Column(length = 60)  private String provincia;
    @Column(length = 60)  private String departamento;

    @Column(length = 5)   private String moneda; // "PEN"/"USD"
    private Double precio;
    private Double area;
    private Integer dormitorios;
}
