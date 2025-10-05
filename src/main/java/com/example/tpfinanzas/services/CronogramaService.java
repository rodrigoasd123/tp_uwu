package com.example.tpfinanzas.services;

import com.example.tpfinanzas.dtos.CronogramaResponseDTO;
import com.example.tpfinanzas.dtos.GenerarCronogramaRequest;

public interface CronogramaService {
    CronogramaResponseDTO generar(GenerarCronogramaRequest req);
}
