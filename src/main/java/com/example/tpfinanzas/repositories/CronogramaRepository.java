package com.example.tpfinanzas.repositories;

import com.example.tpfinanzas.entities.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {
    List<Cronograma> findBySolicitudIdOrderByPeriodoAsc(Long solicitudId);
}
