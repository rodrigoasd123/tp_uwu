package com.example.tpfinanzas.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)  private String tipoDoc;
    @Column(length = 30)  private String nroDoc;
    @Column(length = 80)  private String nombres;
    @Column(length = 80)  private String apellidos;
    @Column(length = 120) private String correo;
    @Column(length = 30)  private String telefono;

    private Double ingresoMensual;
    @Column(length = 40)  private String estadoLaboral;

    private LocalDate fechaRegistro;
}
