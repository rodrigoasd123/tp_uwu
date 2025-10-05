package com.example.tpfinanzas.repositories;

import com.example.tpfinanzas.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> { }
