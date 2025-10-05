package com.example.tpfinanzas.repositories;

import com.example.tpfinanzas.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { }
