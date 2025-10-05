package com.example.tpfinanzas.dtos;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CronogramaResponseDTO {
    private Double tasaMensual;     // i
    private Double cuotaConstante;  // C
    private List<CuotaDTO> cronograma;
}
